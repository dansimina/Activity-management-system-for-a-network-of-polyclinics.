package com.example.source.controller.Altele;

import com.example.source.Model;
import com.example.source.claseTabele.Programare;
import com.example.source.claseTabele.Raport;
import com.example.source.claseTabele.Serviciu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;

public class SceneRaport {
    private Programare programareSelectata;
    private Raport raportPacient;
    @FXML
    private Label labelPacient;
    @FXML
    private TextField numeTextField;
    @FXML
    private TextField prenumeTextField;
    @FXML
    private TextArea textIstoric;
    @FXML
    private TextArea textSimptome;
    @FXML
    private TextArea textDiagnostic;
    @FXML
    private TextArea textRecomandari;
    @FXML
    private ChoiceBox<String> alegeServiciu;
    @FXML
    private TextArea textInvestigatii;
    @FXML
    private Button puneParafa;
    @FXML
    private TableView<Serviciu> tabelInvestigatii;
    @FXML
    private TableColumn<Serviciu, String> numeServiciu;
    @FXML
    private TableColumn<Serviciu, String> detaliiInvestigatii;
    @FXML
    private Button buttonSalveazaPag1;
    @FXML
    private Button buttonSalveazaPag2;
    @FXML
    private Button buttonAdauga;
    @FXML
    private Label labelMesaj1;
    @FXML
    private Label labelMesaj2;
    @FXML
    private Label numeAsistentLabel;
    @FXML
    private Label prenumeAsistentLabel;
    private HashSet<Serviciu> serviciiRaport = new HashSet<>();

    private void populateTabelInvestigatii() {
        numeServiciu.setCellValueFactory(new PropertyValueFactory<>("nume_serviciu"));
        detaliiInvestigatii.setCellValueFactory(new PropertyValueFactory<>("investigatii"));
        tabelInvestigatii.setItems(raportPacient.getServiciiRaport());
    }

    public void adaugaServiciu() {
        String serviciuSelectat = alegeServiciu.getValue();
        ArrayList<Serviciu> servicii = Model.cautaServiciiPoliclinica(Model.getAngajatCurent().getId_policlinica());
        for (Serviciu s : servicii)
            if (s.getNume_serviciu().equals(serviciuSelectat)) {
                s.setInvestigatii(textInvestigatii.getText());
                serviciiRaport.add(s);
                raportPacient.addServicii(s);
            }
        System.out.println(serviciiRaport);
    }

    public void setProgramareSelectata(Programare programare) throws SQLException {
        programareSelectata = programare;
        creazaCheckBoxServicii();

        raportPacient = Model.extrageRaport(programare.getId());
        raportPacient.setServiciiRaport(Model.cautaServiciiRaport(raportPacient.getId()));

        if(Model.getAngajatCurent().getFunctie().equals(Model.ASISTENT_MEDICAL)) {
            puneParafa.setVisible(false);
            raportPacient.setId_asistent(Model.getAsistentCurent().getId());
            Model.updateRaportAsistent(raportPacient);
            System.out.println(raportPacient);
        }
        else
            puneParafa.setVisible(true);
        if (raportPacient.getId_asistent() != 0) {
            int idAsistent = raportPacient.getId_asistent();
            ArrayList<String> s = Model.cautaAsistent(idAsistent);
            numeAsistentLabel.setText(s.get(0));
            prenumeAsistentLabel.setText(s.get(1));
        }
        System.out.println(programare);
        populateTabelInvestigatii();

        if(raportPacient.isParafa()) {
            numeTextField.setEditable(false);
            prenumeTextField.setEditable(false);
            textIstoric.setEditable(false);
            textDiagnostic.setEditable(false);
            textSimptome.setEditable(false);
            textRecomandari.setEditable(false);
            textInvestigatii.setEditable(false);
            buttonSalveazaPag1.setVisible(false);
            buttonSalveazaPag2.setVisible(false);
            buttonAdauga.setVisible(false);
            puneParafa.setText("ANULEAZA");
        }

        System.out.println(raportPacient.getServiciiRaport());
        if (!raportPacient.getNume_medic_recomandare().isEmpty())
            numeTextField.setText(raportPacient.getNume_medic_recomandare());
        if (!raportPacient.getPrenume_medic_recomandare().isEmpty())
            prenumeTextField.setText(raportPacient.getPrenume_medic_recomandare());
        if (!raportPacient.getIstoric().isEmpty())
            textIstoric.setText(raportPacient.getIstoric());
        if (!raportPacient.getDiagnostic().isEmpty())
            textDiagnostic.setText(raportPacient.getDiagnostic());
        if (!raportPacient.getSimptome().isEmpty())
            textSimptome.setText(raportPacient.getSimptome());
        if (!raportPacient.getRecomandari().isEmpty())
            textRecomandari.setText(raportPacient.getRecomandari());
        labelPacient.setText(programareSelectata.getNume() + " " + programareSelectata.getPrenume());
    }

    public void updateRaport() throws SQLException {
        raportPacient.setNume_medic_recomandare(numeTextField.getText());
        raportPacient.setPrenume_medic_recomandare(prenumeTextField.getText());
        raportPacient.setIstoric(textIstoric.getText());
        raportPacient.setDiagnostic(textDiagnostic.getText());
        raportPacient.setSimptome(textSimptome.getText());
        raportPacient.setRecomandari(textRecomandari.getText());
        Model.updateRaport(raportPacient);
        labelMesaj1.setText("SALVAT CU SUCCES");
    }

    private void creazaCheckBoxServicii() {
        alegeServiciu.getItems().clear();
        ArrayList<Serviciu> serviciiMedic = Model.cautaServiciiPoliclinica(programareSelectata.getId_policlinica());
        alegeServiciu.setValue(serviciiMedic.get(0).getNume_serviciu());
        String[] nume_servicii = new String[serviciiMedic.size()];
        for (int i = 0; i < serviciiMedic.size(); i++)
            nume_servicii[i] = serviciiMedic.get(i).getNume_serviciu();
        alegeServiciu.getItems().addAll(nume_servicii);
    }

    public void updateServicii() throws SQLException {
        Model.updateServiciiRaport(raportPacient.getId(), serviciiRaport);
        serviciiRaport.clear();
        labelMesaj2.setText("SALVAT CU SUCCES");
    }

    public void puneParafa() throws SQLException {
        if(raportPacient.isParafa() == false) {
            raportPacient.setParafa(true);
            numeTextField.setEditable(false);
            prenumeTextField.setEditable(false);
            textIstoric.setEditable(false);
            textDiagnostic.setEditable(false);
            textSimptome.setEditable(false);
            textRecomandari.setEditable(false);
            textInvestigatii.setEditable(false);
            buttonSalveazaPag1.setVisible(false);
            buttonSalveazaPag2.setVisible(false);
            buttonAdauga.setVisible(false);
            updateRaport();
            puneParafa.setText("ANULEAZA");
        }
        else {
            raportPacient.setParafa(false);
            numeTextField.setEditable(true);
            prenumeTextField.setEditable(true);
            textIstoric.setEditable(true);
            textDiagnostic.setEditable(true);
            textSimptome.setEditable(true);
            textRecomandari.setEditable(true);
            textInvestigatii.setEditable(true);
            buttonSalveazaPag1.setVisible(true);
            buttonSalveazaPag2.setVisible(true);
            buttonAdauga.setVisible(true);
            updateRaport();
            puneParafa.setText("PARAFA");
        }
    }

    public void scoateText() {
        labelMesaj1.setText("");
        labelMesaj2.setText("");
    }

    public void goBack(ActionEvent event) throws IOException {
        String scenePath;
        if (Model.getAngajatCurent().getFunctie().equals(Model.MEDIC))
            scenePath = "/com.example.source/scene-medic-view.fxml";
        else
            scenePath = "/com.example.source/scene-asistent-medical-view.fxml";
        Model.goToMainMenu(event, scenePath);
    }
}
