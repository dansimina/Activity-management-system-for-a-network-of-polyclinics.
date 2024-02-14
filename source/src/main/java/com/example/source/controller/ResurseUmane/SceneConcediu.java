package com.example.source.controller.ResurseUmane;

import com.example.source.Model;
import com.example.source.claseTabele.Angajat;
import com.example.source.claseTabele.Concediu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SceneConcediu implements Initializable {
    @FXML
    private Label labelAngajat;
    @FXML
    private Button butonInapoi;
    @FXML
    private TableView<Concediu> tabel;
    @FXML
    private TableColumn<Concediu, Date> data_inceput;
    @FXML
    private TableColumn<Concediu, Date> data_sfarsit;
    private Angajat angajatSelectat;
    ObservableList<Concediu> concedii = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        angajatSelectat = Model.getAngajatSelectat();
        labelAngajat.setText(angajatSelectat.toString());
        try {
            System.out.println(angajatSelectat);
            concedii = Model.listaConcedii(angajatSelectat.getId_utilizator());
            populateTabel();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setAngajatSelectat(Angajat angajatSelectat) {
        this.angajatSelectat = angajatSelectat;
        labelAngajat.setText(angajatSelectat.getFunctie() + ": " + angajatSelectat.getNume() + " " + angajatSelectat.getPrenume());
    }

    public void goBack(ActionEvent event) throws IOException, SQLException {
        if(Model.getAngajatCurent().getFunctie().equals(Model.RESURSE_UMANE))
            Model.switchToWindowOrare(event, angajatSelectat);
        else
            Model.goToMainMenu(event, "/com.example.source/scene-economic-view.fxml");
    }

    private void populateTabel() {
        data_inceput.setCellValueFactory(new PropertyValueFactory<>("data_inceput"));
        data_sfarsit.setCellValueFactory(new PropertyValueFactory<>("data_sfarsit"));
        tabel.setItems(concedii);
    }
}
