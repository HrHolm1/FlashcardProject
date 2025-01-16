package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ChooseDeckController {

    @FXML
    private ListView<String> deckListView;

    @FXML
    private void initialize() {
        // Hent alle decks fra DeckManager
        DeckManager deckManager = DeckManager.getInstance();

        // Tilføj navnene på alle decks til ListView
        for (FlashcardDeck deck : deckManager.getDecks()) {
            deckListView.getItems().add(deck.getFlashcardDeckName());
        }

        // Tillad kun ét valg ad gangen
        deckListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void handlePlayDeck() {
        // Tjek om brugeren har valgt et deck
        String selectedDeckName = deckListView.getSelectionModel().getSelectedItem();
        if (selectedDeckName == null) {
            showAlert("No Deck Selected", "Please select a deck to play.");
            return;
        }

        // Hent det valgte deck fra DeckManager
        FlashcardDeck selectedDeck = DeckManager.getInstance().getDeckByName(selectedDeckName);

        if (selectedDeck == null) {
            showAlert("Deck Not Found", "The selected deck could not be found.");
            return;
        }

        // Start spillet med det valgte deck
        startTrainingSession(selectedDeck);
    }

    private void startTrainingSession(FlashcardDeck deck) {
        try {
            // Opret en ny view til træning
            FXMLLoader loader = new FXMLLoader(getClass().getResource("training-view.fxml"));
            VBox trainingView = loader.load();
            TrainingController trainingController = loader.getController();

            // Send det valgte deck til TrainingController
            trainingController.setFlashcardDeck(deck);

            // Find root layout og opdater kun centerområdet
            BorderPane root = (BorderPane) deckListView.getScene().getRoot();
            root.setCenter(trainingView); // Skift kun centerområdet, så resten af UI'et bliver

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
