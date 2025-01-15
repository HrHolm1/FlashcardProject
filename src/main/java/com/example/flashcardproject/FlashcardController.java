package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
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
        updateTrainingController("Easy");
    }

    @FXML
    private void handleMedium() {
        statistics.answerStatistics("Medium");
        System.out.println("Medium button clicked!");
        updateTrainingController("Medium");
    }

    @FXML
    private void handleHard() {
        statistics.answerStatistics("Hard");
        System.out.println("Hard button clicked!");
        updateTrainingController("Hard");
    }

    @FXML
    private void handleLearned() {
        statistics.answerStatistics("Learned");
        System.out.println("Learned button clicked!");

        // Videregiv svaret til TrainingController for at opdatere rækkefølgen
        updateTrainingController("Learned");
    }

    private void updateTrainingController(String answerType) {
        if (trainingController != null) {
            trainingController.updateDeckOrder(answerType);  // Opdater deckrækkefølgen i TrainingController
        }
    }

    @FXML
    private void handleImportFiles() {
        // Brug FileChooser til at vælge tekstfil
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setTitle("Open text File");
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        // Hvis en fil er valgt
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            System.out.println("Valgt fil: " + filePath);

            // Brug DirectoryChooser til at vælge billede-mappe
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Vælg billede mappe");
            File selectedDirectory = directoryChooser.showDialog(new Stage());

            // Hvis en mappe er valgt
            if (selectedDirectory != null) {
                String imageDirectory = selectedDirectory.getAbsolutePath();
                System.out.println("Valgt billede mappe: " + imageDirectory);

                // Opret AnkiImporter med den valgte fil og billede-mappe
                importer = new AnkiImporter(filePath, imageDirectory);

                try {
                    // Importer decks og billeder
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
            } else {
                System.out.println("Ingen billede-mappe valgt.");
            }
        } else {
            System.out.println("Ingen fil valgt.");
        }
    }

    @FXML
    private void helpWindow() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help & Information");
        alert.setHeaderText(null);
        alert.setContentText("Below are 4 buttons, Again, Hard, Medium & Easy. " +
                "These buttons determine how often a given card is shown. " +
                "If you choose 'easy', then that card will appear later, " +
                "if you choose hard then that card will appear sooner. " +
                "Have fun !!! ");
        alert.showAndWait();
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
