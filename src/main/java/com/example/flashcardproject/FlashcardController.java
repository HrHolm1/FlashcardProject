package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class FlashcardController {
    @FXML
    private Label welcomeText;

    private TrainingController trainingController;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private void initialize() {
        FXMLLoader fmlloader = new FXMLLoader(getClass().getResource("/fxml/training-view.fxml"));
        trainingController = fmlloader.getController();

    }
}