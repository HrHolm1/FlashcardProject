package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TrainingController {

    @FXML
    private Label answerLabel;
    @FXML
    private ImageView questionImage;
    @FXML
    private Label questionLabel;
    @FXML
    private Button showAnswerButton;
    @FXML
    private Button nextCardButton;

    private FlashcardDeck currentDeck;  // Decket, der arbejdes med
    private int currentIndex = 0;

    // Setter decket og viser det første kort
    public void setFlashcardDeck(FlashcardDeck deck) {
        if (deck == null || deck.getFlashcards().isEmpty()) {
            System.err.println("Decket er tomt eller ikke initialiseret!");
            return;
        }
        this.currentDeck = deck;
        this.currentIndex = 0;  // Start ved første kort
        showFlashcard(currentIndex);  // Vis første kort
    }

    @FXML
    private void handleShowAnswer() {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        Flashcard currentCard = currentDeck.getFlashcards().get(currentIndex);
        answerLabel.setText(currentCard.getAnswer());
        showAnswerButton.setDisable(true);  // Deaktiver knappen efter at svaret er vist
    }

    @FXML
    private void handleNextCard() {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        // Tjek om vi er ved sidste kort, og vis det næste kort
        if (currentIndex < currentDeck.getFlashcards().size() - 1) {
            currentIndex++;
            showFlashcard(currentIndex);
        } else {
            System.out.println("Der er ikke flere kort.");
            nextCardButton.setDisable(true); // Deaktiver 'Next Card' knappen, når der ikke er flere kort
        }
    }

    private void showFlashcard(int index) {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        // Hent det aktuelle kort
        Flashcard currentCard = currentDeck.getFlashcards().get(index);
        questionLabel.setText(currentCard.getQuestion());  // Vis spørgsmålet (maleriets titel)

        // Vis billede, hvis det er tilgængeligt
        String basePath = "C:/Users/Rambo/Documents/Flashcards/greatartists/";  // Juster til din billedmappe
        String imagePath = basePath + currentCard.getImagePath();

        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image("file:" + imagePath);  // Sørg for at billedsti er korrekt
            questionImage.setImage(image);
        } else {
            questionImage.setImage(null);  // Ingen billede
        }

        answerLabel.setText("Answer is shown here");  // Resæt svaret
        showAnswerButton.setDisable(false);  // Aktivér "Vis svar"-knappen igen
    }
}
