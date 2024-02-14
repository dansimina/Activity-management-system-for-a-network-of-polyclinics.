package com.example.source.controller.ResurseUmane;

import com.example.source.Model;
import com.example.source.claseTabele.Angajat;
import com.example.source.claseTabele.Medic;
import com.example.source.claseTabele.Specialitati;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class SceneOrarConcediu implements Initializable {

    @FXML
    private Label labelAngajat;
    @FXML
    private Button butonInapoi;
    @FXML
    private Button butonConcediu;
    @FXML
    private Button butonOrar;
    @FXML
    private Button butonAfiseazaConcediu;
    @FXML
    private ChoiceBox<String> alegeZi;
    @FXML
    private ChoiceBox<String> oraInceput;
    @FXML
    private ChoiceBox<String> oraSfarsit;
    @FXML
    private Label labelZiSelectata;
    @FXML
    private Label labelOraInceput;
    @FXML
    private Label labelOraSfarsit;
    @FXML
    private Label labelZiInceput;
    @FXML
    private Label labelZiSfarsit;
    @FXML
    private Label labelConfirmareConcediu;
    @FXML
    private Label labelConfirmareOrar;
    @FXML
    private DatePicker concediuInceput;
    @FXML
    private DatePicker concediuSfarsit;
    @FXML
    private DatePicker dataOrar;
    private String[] zile = new String[]{"Duminica", "Luni", "Marti", "Miercuri", "Joi", "Vineri", "Sambata"};
    private String[] ore = new String[]{"07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00"};
    private static int DIFERENTA_ORE = 12;
    private static int ZILE_MAXIME_CONCEDIU = 14;
    private Angajat angajatSelectat;

    public void setAngajatSelectat(Angajat angajatSelectat) throws SQLException {
        this.angajatSelectat = angajatSelectat;
        String text = angajatSelectat.getFunctie() + ": " + angajatSelectat.getNume() + " " + angajatSelectat.getPrenume();
        if (angajatSelectat.getFunctie().equals(Model.MEDIC)) {
            text += "\nSpecialitati: ";
            ObservableList<Specialitati> sp = Model.listaSpecialitatiMedic(Model.cautaMedic(this.angajatSelectat.getId()).getId());
            for (Specialitati it : sp)
                text += it.getNume_specialitate() + ", ";
            labelAngajat.setFont(new Font(labelAngajat.getFont().getName(), 12));
        }
        labelAngajat.setText(text.substring(0,text.length()-2));
        setOrar(angajatSelectat.getFunctie().trim().equals("medic"));
    }

    public void goBack(ActionEvent event) throws IOException {
        String scenePath = "/com.example.source/scene-resurse-umane-view.fxml";
        Model.goToMainMenu(event, scenePath);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        angajatSelectat = Model.getAngajatCurent();
        setOrar(angajatSelectat.getFunctie().trim().equals("medic"));
        labelConfirmareConcediu.setText("");
    }

    public void alegeZiua(ActionEvent event) {
        String zi = alegeZi.getValue();
        labelZiSelectata.setText(zi);
        oraInceput.setValue(ore[0]);
        oraSfarsit.setValue(ore[DIFERENTA_ORE]);
    }

    public void butonConcediuApasat(ActionEvent event) {
        LocalDate ziInceput = concediuInceput.getValue();
        LocalDate ziSfarsit = concediuSfarsit.getValue();
        if (ziSfarsit == null || ziSfarsit == null)
            return;
        long nrZileConcediu = DAYS.between(ziInceput, ziSfarsit);
        if (ziSfarsit.isAfter(ziInceput) && (int) nrZileConcediu <= ZILE_MAXIME_CONCEDIU) {
            System.out.println(ziInceput + "\n" + ziSfarsit);
            System.out.println(nrZileConcediu);
            if (Model.concediuCorect(angajatSelectat.getId(), Date.valueOf(ziInceput), Date.valueOf(ziSfarsit))) {
                Model.adaugaConcediu(angajatSelectat.getId(), ziInceput, ziSfarsit);
                labelConfirmareConcediu.setText("SUCCES!");
            } else {
                labelConfirmareConcediu.setText("Eroare");
            }
        }
    }

    public void butonOrarApasat(ActionEvent event) throws SQLException {
        String zi = alegeZi.getValue();
        Medic medicSelectat = Model.cautaMedic(angajatSelectat.getId());
        LocalDate data = dataOrar.getValue();
        String ora_inceput = oraInceput.getValue() + ":00";
        String ora_sfarsit = oraSfarsit.getValue() + ":00";
        if (data == null)
            Model.modificaOrarMedic(medicSelectat.getId(), 0, zi, Time.valueOf(ora_inceput), Time.valueOf(ora_sfarsit));
        else
            Model.modificaOrarMedic(medicSelectat.getId(), 1, data.toString(), Time.valueOf(ora_inceput), Time.valueOf(ora_sfarsit));
        labelConfirmareOrar.setText("SUCCES!");
    }

    public void afiseazaConcediu(ActionEvent event) throws IOException {
        Model.switchToWindowConcedii(event, angajatSelectat);
    }

    public void stergeTextLabel() {
        labelConfirmareConcediu.setText("");
        labelConfirmareOrar.setText("");
    }

    public void setOrar(boolean value) {
        labelConfirmareConcediu.setText("");

        alegeZi.setVisible(value);
        labelZiSelectata.setVisible(value);
        labelOraInceput.setVisible(value);
        labelOraSfarsit.setVisible(value);
        oraSfarsit.setVisible(value);
        oraInceput.setVisible(value);
        butonOrar.setVisible(value);
        dataOrar.setVisible(value);
        if (value) {
            alegeZi.setValue(zile[0]);
            String zi = alegeZi.getValue();
            labelZiSelectata.setText(zi);
            alegeZi.getItems().addAll(zile);
            alegeZi.setOnAction(this::alegeZiua);

            oraInceput.setValue(ore[0]);
            oraInceput.getItems().addAll(ore);
            oraSfarsit.setValue(ore[DIFERENTA_ORE]);
            oraSfarsit.getItems().addAll(ore);
        }
    }
}
