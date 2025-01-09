package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;

public class FlashcardController {

    private Statistics statistics = new Statistics();

    AnkiImporter importer = new AnkiImporter("C:\\Users\\Rambo\\Desktop\\Great Works of Art__Artists2024.txt", "C:\\Users\\Rambo\\Desktop\\greatartists");

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
            // Importer tekstfilen
            List<Flashcard> flashcards = importer.importFlashcards();
            System.out.println("Antal importerede kort: " + flashcards.size());

            // Importer billederne
            importer.importImages();
            System.out.println("Billeder importeret.");

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