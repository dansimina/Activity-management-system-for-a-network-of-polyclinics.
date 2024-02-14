package com.example.source.controller.Altele;

import com.example.source.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SceneLogin {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label labelErrorLogIn;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Button buttonLogIn;

    public void switchToSceneUser(ActionEvent event) throws IOException, SQLException {
        if (Model.logIn(textFieldUsername.getText(), passwordFieldPassword.getText())) {
            Model.extrageAngajatDupaUtilizator(Model.getUtilizatorCurent().getId());
            if (Model.getUtilizatorCurent().getRol().compareTo(Model.SUPERADMIN) == 0) {
                switchToSceneSuperAdmin(event);
            } else if (Model.getUtilizatorCurent().getRol().compareTo(Model.ADMIN) == 0) {
                switchToSceneSuperAdmin(event);
            } else if (Model.getUtilizatorCurent().getRol().compareTo(Model.UTILIZATOR) == 0) {
                if (Model.getAngajatCurent().getFunctie().compareTo(Model.MEDIC) == 0) {
                    Model.extrageMedic(Model.getAngajatCurent().getId());
                    switchToSceneMedic(event);
                } else if (Model.getAngajatCurent().getFunctie().compareTo(Model.RESURSE_UMANE) == 0) {
                    switchToSceneResurseUmane(event);
                } else if (Model.getAngajatCurent().getFunctie().compareTo(Model.ECONOMIC) == 0) {
                    switchToSceneEconomic(event);
                } else if (Model.getAngajatCurent().getFunctie().compareTo(Model.ASISTENT_MEDICAL) == 0) {
                    Model.extrageAsistentMedical(Model.getAngajatCurent().getId());
                    switchToSceneAsistentMedical(event);
                } else if (Model.getAngajatCurent().getFunctie().compareTo(Model.RECEPTIONER) == 0) {
                    switchToSceneReceptioner(event);
                }
            }
        } else if (textFieldUsername.getText().isEmpty() && passwordFieldPassword.getText().isEmpty()) {
            labelErrorLogIn.setText("Introduceti numele de utilizator si parola!");
        } else {
            labelErrorLogIn.setText("Nume de utilizator sau parola incorecte!");
        }
    }

    private void switchToSceneSuperAdmin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-super-admin-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void switchToSceneMedic(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-medic-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneResurseUmane(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-resurse-umane-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneEconomic(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-economic-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneAsistentMedical(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-asistent-medical-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneReceptioner(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-receptioner-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
