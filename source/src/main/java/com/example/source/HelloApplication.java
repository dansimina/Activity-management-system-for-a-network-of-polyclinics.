package com.example.source;

import java.sql.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        try {
            stage.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("/com.example.source/scene-login-view.fxml"));
            stage.setTitle("Policlinici");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}

// de adaugat salariu si nr ore angajat