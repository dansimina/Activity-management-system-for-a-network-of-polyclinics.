package com.example.source.controller.Admin;

import com.example.source.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SceneSuperAdmin implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label labelAdresa;

    @FXML
    private Label labelCnp;

    @FXML
    private Label labelDataAngajarii;

    @FXML
    private Label labelDepartament;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelIban;

    @FXML
    private Label labelNume;

    @FXML
    private Label labelPrenume;

    @FXML
    private Label labelTelefon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labelNume.setText(Model.getUtilizatorCurent().getNume());
        labelPrenume.setText(Model.getUtilizatorCurent().getPrenume());
        labelDepartament.setText(Model.getUtilizatorCurent().getDepartament());
        labelAdresa.setText(Model.getUtilizatorCurent().getAdresa());
        labelCnp.setText(Model.getUtilizatorCurent().getCnp());
        labelTelefon.setText(Model.getUtilizatorCurent().getTelefon());
        labelEmail.setText(Model.getUtilizatorCurent().getEmail());
        labelIban.setText(Model.getUtilizatorCurent().getIban());
        labelDataAngajarii.setText(Model.getUtilizatorCurent().getData_angajarii());
    }

    public void goToUtilizatori(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-sa-utilizatori-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void goToPoliclinici(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-sa-policlinici-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneLogin(ActionEvent event) throws IOException {
        String scene = "/com.example.source/scene-login-view.fxml";
        Model.logOut(event, scene);
    }
}
