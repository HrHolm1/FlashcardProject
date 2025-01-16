package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.control.Label;

import java.io.File;

public class CreateDeckController {

    @FXML
    private TextField deckNameField;
    @FXML
    private TextField questionField;
    @FXML
    private TextField answerField;
    @FXML
    private TextField topicField;
    @FXML
    private Label imagePathLabel;

    private FlashcardDeck customDeck;

    @FXML
    private void initialize() {
        customDeck = null;  // Ingen deck endnu
    }

    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        fileChooser.setTitle("Choose an Image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imagePathLabel.setText(selectedFile.getAbsolutePath());
        } else {
            imagePathLabel.setText("No image selected");
        }
    }

    @FXML
    private void handleAddFlashcard() {
        String question = questionField.getText();
        String answer = answerField.getText();
        String topic = topicField.getText();
        String imagePath = imagePathLabel.getText();

        if (question.isEmpty() || answer.isEmpty() || topic.isEmpty() || imagePath.equals("No image selected")) {
            showAlert("Missing Fields", "Please fill out all fields and choose an image.");
            return;
        }

        // Sørg for, at et deck er initialiseret
        if (customDeck == null) {
            String deckName = deckNameField.getText();
            if (deckName.isEmpty()) {
                showAlert("Missing Deck Name", "Please provide a name for the deck.");
                return;
            }
            customDeck = new FlashcardDeck(deckName);
        }

        // Tilføj flashcard til deck
        int index = customDeck.getFlashcards().size();  // Indeks er rækkefølgen
        Flashcard newCard = new Flashcard(question, answer, imagePath, topic, index);
        customDeck.addFlashcard(newCard);

        // Ryd felterne for at tilføje flere kort
        questionField.clear();
        answerField.clear();
        topicField.clear();
        imagePathLabel.setText("No image selected");

        showAlert("Flashcard Added", "The flashcard was successfully added to the deck.");
    }

    @FXML
    private void handleSaveDeck() {
        if (customDeck == null || customDeck.getFlashcards().isEmpty()) {
            showAlert("Empty Deck", "Please add at least one flashcard before saving.");
            return;
        }

        // Tilføj decket til DeckManager
        DeckManager.getInstance().addDeck(customDeck);

        System.out.println("Deck saved: " + customDeck.getFlashcardDeckName());
        System.out.println("Total cards: " + customDeck.getFlashcards().size());

        // Ryd alt
        deckNameField.clear();
        customDeck = null;

        showAlert("Deck Saved", "The deck was successfully saved.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}