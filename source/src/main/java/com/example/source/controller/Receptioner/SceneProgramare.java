package com.example.source.controller.Receptioner;

import com.example.source.claseTabele.*;
import com.example.source.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class SceneProgramare implements Initializable {
    @FXML
    private Label labelPacient;
    @FXML
    private Label labelMedic;
    @FXML
    private DatePicker dataProgramare;
    @FXML
    private ChoiceBox<String> alegeMedic;
    @FXML
    private ChoiceBox<String> alegeServiciu;
    @FXML
    private Label labelMesaj;
    @FXML
    private Label labelMesajAdd;
    @FXML
    private Label labelMesajPret;

    private ArrayList<Medic> medici;
    private Medic medicSelectat;
    private Pacient pacientSelectat;
    private HashSet<Serviciu> serviciiProgramare = new HashSet<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pacientSelectat = Model.getPacientSelectat();
        try {
            medici = Model.listaMedici();
            creazaCheckBoxMedici();
            creazaCheckBoxServicii();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        labelMesajAdd.setVisible(false);
    }

    public void setPacientSelectat(Pacient pacient) {
        pacientSelectat = pacient;
        labelPacient.setText(pacient.getNume() + " " + pacient.getPrenume());
    }

    public void alegeMedic(ActionEvent event) {
        labelMedic.setText(alegeMedic.getValue());
        for (Medic m : medici) {
            String nume_prenume = m.getNume() + " " + m.getPrenume();
            if (nume_prenume.equals(labelMedic.getText()))
                medicSelectat = m;
        }
        restrictieData();
        creazaCheckBoxServicii();
        serviciiProgramare = new HashSet<>();
    }

    private void creazaCheckBoxServicii() {
        alegeServiciu.getItems().clear();
        ArrayList<Serviciu> serviciiMedic = medicSelectat.getServicii();
        alegeServiciu.setValue(serviciiMedic.get(0).getNume_serviciu() + "\n\nPret: " + serviciiMedic.get(0).getPret() + " LEI     Durata: " + serviciiMedic.get(0).getDurata());
        String[] nume_servicii = new String[serviciiMedic.size()];
        for (int i = 0; i < serviciiMedic.size(); i++)
            nume_servicii[i] = serviciiMedic.get(i).getNume_serviciu() + "\n\nPret: " + serviciiMedic.get(i).getPret() + " LEI     Durata: " + serviciiMedic.get(i).getDurata();
        alegeServiciu.getItems().addAll(nume_servicii);
    }

    public void creazaCheckBoxMedici() {
        alegeMedic.setValue(medici.get(0).getNume() + " " + medici.get(0).getPrenume());
        labelMedic.setText(alegeMedic.getValue());
        medici.get(0).afiseazaServicii();
        medicSelectat = medici.get(0);
        String[] nume_medici = new String[medici.size()];
        for (int i = 0; i < medici.size(); i++)
            nume_medici[i] = medici.get(i).getNume() + " " + medici.get(i).getPrenume();
        alegeMedic.getItems().addAll(nume_medici);
        alegeMedic.setOnAction(this::alegeMedic);
        restrictieData();
    }

    public void adaugaServiciu() {
        String serviciuSelectat = alegeServiciu.getValue();
        serviciuSelectat = serviciuSelectat.split("\n")[0];
        System.out.println(serviciuSelectat);
        ArrayList<Serviciu> servicii = medicSelectat.getServicii();
        for (Serviciu s : servicii)
            if (s.getNume_serviciu().equals(serviciuSelectat))
                serviciiProgramare.add(s);
        labelMesajAdd.setVisible(true);
    }

    private void restrictieData() {
        ArrayList<DataConcediu> concedii = Model.angajatInConcediu(medicSelectat.getId_angajat());
        dataProgramare.setDayCellFactory(picker -> new DateCell() { //date de azi incolo
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate azi = LocalDate.now();
                for (DataConcediu c : concedii) {
                    LocalDate data_inceput = c.getData_inceput().toLocalDate();
                    LocalDate data_sfarsit = c.getData_sfarsit().toLocalDate();
                    if (!isDisabled())
                        setDisable(empty || (date.compareTo(azi) < 0) || (date.compareTo(data_inceput) >= 0 && date.compareTo(data_sfarsit) <= 0));
                }
                if (!isDisabled())
                    setDisable(empty || date.isBefore(azi));
            }
        });
    }

    public void clickServicii() {
        if(labelMesajAdd.isVisible())
            labelMesajAdd.setVisible(false);

        labelMesaj.setText("");
        labelMesajPret.setText("");
    }

    public void faProgramare() {
        LocalDate dataSelectata = dataProgramare.getValue();
        System.out.println(dataSelectata);
        System.out.println(serviciiProgramare);
        if (dataSelectata != null && !serviciiProgramare.isEmpty()) {
            IntervalOrar orar = Model.extrageOrarMedicZiProgramare(medicSelectat.getId(), Date.valueOf(dataSelectata));
            Time timp = Model.extrageFinalProgramari(medicSelectat.getId(), dataSelectata, orar.getOra_inceput());
            Time timpInceput = Time.valueOf(timp.toLocalTime());
            for (Serviciu s : serviciiProgramare)
                timp.setTime(timp.getTime() + s.getDurata().getTime() - Time.valueOf("00:00:00").getTime());
            System.out.println(timpInceput + " " + timp + " " + orar.getOra_sfarsit());

            if (timp.toLocalTime().isAfter(orar.getOra_sfarsit().toLocalTime())) {
                labelMesaj.setText("TIMP INSUFICIENT SELECTATI ALTA DATA");
            } else {
                Model.inserareProgramare(Model.idPoliclinicaDeCareApartineMedic(medicSelectat.getId()), Model.getAngajatCurent().getId(), pacientSelectat.getId(), medicSelectat.getId(), Date.valueOf(dataSelectata), timpInceput, timp);
                labelMesaj.setText("PROGRAMARE EFECTUATA CU SUCCES " + timpInceput + " - " + timp);
                labelMesajPret.setText("PRET: " + sumaServicii() + " LEI");
            }

        } else {
            labelMesaj.setText("SELECTATI DATA SI SERVICII");
        }
    }

    private int sumaServicii() {
        int suma = 0;
        for(Serviciu s: serviciiProgramare) {
            suma += s.getPret();
        }

        return suma;
    }

    public void goBack(ActionEvent event) throws IOException {
        String scenePath = "/com.example.source/scene-receptioner-view.fxml";
        Model.goToMainMenu(event, scenePath);
    }
}
