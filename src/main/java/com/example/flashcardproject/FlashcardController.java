package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardController {

    private Statistics statistics = new Statistics();
    private AnkiImporter importer;

    @FXML
    private BorderPane root;

    @FXML
    private TrainingController trainingController;

    private List<FlashcardDeck> decks = new ArrayList<>();

    @FXML
    private void initialize() {
        importer = new AnkiImporter("C:\\Users\\Rambo\\Documents\\Flashcards\\Great Works of Art__Artists2024.txt", "C:\\Users\\Rambo\\Documents\\Flashcards\\greatartists");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("training-view.fxml"));
            VBox trainingView = loader.load();
            root.setCenter(trainingView);
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

    @FXML
    private void handleImportFiles() {
        try {
            // Importer et nyt deck
            List<FlashcardDeck> importedDecks = importer.importFlashcardDecks();
            FlashcardDeck newDeck = importedDecks.get(0); // Der er kun ét deck i listen

            // Tilføj flashcards til decket
            System.out.println("Antal importerede kort: " + newDeck.getFlashcards().size());

            // Importer billeder (hvis nødvendigt)
            importer.importImages();
            System.out.println("Billeder importeret.");

            // Kontroller, at trainingController er initialiseret
            if (trainingController != null) {
                trainingController.setFlashcardDeck(newDeck); // Send hele decket
                System.out.println("Deck blev sendt til TrainingController.");
            } else {
                System.err.println("TrainingController er ikke blevet initialiseret korrekt.");
            }
        } catch (IOException e) {
            showAlert("Fejl", "Der opstod en fejl under import af filer.", e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
