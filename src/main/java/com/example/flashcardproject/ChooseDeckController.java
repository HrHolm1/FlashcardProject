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

    private FlashcardController flashcardController;

    public void setFlashcardController(FlashcardController flashcardController) {
        this.flashcardController = flashcardController;
    }


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
        String selectedDeckName = deckListView.getSelectionModel().getSelectedItem();
        if (selectedDeckName == null) {
            showAlert("No Deck Selected", "Please select a deck to play.");
            return;
        }

        FlashcardDeck selectedDeck = DeckManager.getInstance().getDeckByName(selectedDeckName);
        if (selectedDeck == null || selectedDeck.getFlashcards().isEmpty()) {
            showAlert("Deck Not Found", "The selected deck could not be found.");
            return;
        }

        // Fortæl FlashcardController, hvilket deck der er valgt
        if (flashcardController != null && flashcardController.getTrainingController() != null) {
            flashcardController.getTrainingController().setFlashcardDeck(selectedDeck);
            System.out.println("Deck blev sendt til TrainingController fra ChooseDeckController: " + selectedDeck.getFlashcardDeckName());
        } else {
            System.err.println("FlashcardController eller TrainingController er ikke initialiseret korrekt.");
        }

        // Start træningssessionen og vis UI
        startTrainingSession(selectedDeck);
    }

    private void startTrainingSession(FlashcardDeck deck) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("training-view.fxml"));
            VBox trainingView = loader.load();
            TrainingController trainingController = loader.getController();

            // Brug singleton-instansen af TrainingSession
            TrainingSession trainingSession = TrainingSession.getInstance();
            trainingSession.startSession(deck.getFlashcardDeckName());

            trainingController.setTrainingSession(trainingSession);
            trainingController.setFlashcardDeck(deck);

            BorderPane root = (BorderPane) deckListView.getScene().getRoot();
            root.setCenter(trainingView);
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

    @FXML
    private void handleRemoveDeck() {
        String selectedDeckName = deckListView.getSelectionModel().getSelectedItem();

        if (selectedDeckName == null) {
            showAlert("No Deck Selected", "Please select a deck to remove.");
            return;
        }

        // Bekræft med brugeren
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirm Removal");
        confirmation.setHeaderText("Are you sure you want to remove this deck?");
        confirmation.setContentText("Deck: " + selectedDeckName);

        confirmation.showAndWait().ifPresent(response -> {
            if (response.getText().equalsIgnoreCase("OK")) {
                // Fjern deck fra DeckManager
                DeckManager.getInstance().removeDeck(selectedDeckName);

                // Opdater ListView
                deckListView.getItems().remove(selectedDeckName);
                showAlert("Deck Removed", "The deck '" + selectedDeckName + "' has been removed.");
            }
        });
    }

}
