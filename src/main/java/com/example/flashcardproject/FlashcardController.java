package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class FlashcardController {

    private Statistics statistics = new Statistics();
    private AnkiImporter importer;

    @FXML
    private BorderPane root;

    // Reference til TrainingController for at opdatere flashcards
    @FXML
    private TrainingController trainingController;

    @FXML
    private void initialize() {
        // Initialiser AnkiImporter med de rette stier
        importer = new AnkiImporter("C:\\Users\\Rambo\\Documents\\Flashcards\\Great Works of Art__Artists2024.txt", "C:\\Users\\Rambo\\Documents\\Flashcards\\greatartists");

        try {
            // Loader training-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("training-view.fxml"));
            VBox trainingView = loader.load(); // Læs roden som VBox, da det er typen i FXML'en

            // Tilføjer trainingView til midten af BorderPane
            root.setCenter(trainingView);

            // Brug den allerede injicerede trainingController
            trainingController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEasy() {
        statistics.answerStatistics("Easy");
        System.out.println("Easy button clicked!");
    }

    @FXML
    private void handleMedium() {
        statistics.answerStatistics("Medium");
        System.out.println("Medium button clicked!");
    }

    @FXML
    private void handleHard() {
        statistics.answerStatistics("Hard");
        System.out.println("Hard button clicked!");
    }

    @FXML
    private void handleAgain() {
        statistics.answerStatistics("Again");
        System.out.println("Again button clicked!");
    }

    // Metode til at importere tekstfilen og billeder
    @FXML
    private void handleImportFiles() {
        try {
            // Importer flashcards fra tekstfilen
            List<Flashcard> flashcards = importer.importFlashcards();
            System.out.println("Antal importerede kort: " + flashcards.size());

            // Importer billeder
            importer.importImages();
            System.out.println("Billeder importeret.");

            // Kontroller, at trainingController er initialiseret
            if (trainingController != null) {
                trainingController.setFlashcards(flashcards);
                System.out.println("Flashcards blev sendt til TrainingController.");
            } else {
                System.err.println("TrainingController er ikke blevet initialiseret korrekt.");
            }
        } catch (IOException e) {
            showAlert("Fejl", "Der opstod en fejl under import af filer.", e.getMessage());
        }
    }

    // Håndter fejl med en simpel Alert dialog
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
