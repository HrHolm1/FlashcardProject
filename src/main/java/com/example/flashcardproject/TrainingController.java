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

    private FlashcardDeck currentDeck;
    private TrainingSession trainingSession;
    private int currentIndex;

    public TrainingController() {
        trainingSession = new TrainingSession();  // Initialiser TrainingSession
    }

    // Sætter decket og starter sessionen
    public void setFlashcardDeck(FlashcardDeck deck) {
        if (deck == null || deck.getFlashcards().isEmpty()) {
            System.err.println("Decket er tomt eller ikke initialiseret!");
            return;
        }
        this.currentDeck = deck;
        trainingSession.startSession(deck);  // Start sessionen med det importerede deck
        currentIndex = 0;  // Sæt startindeks
        showNextCard();  // Vis første kort
    }

    @FXML
    private void handleShowAnswer() {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        // Find det nuværende kort
        Flashcard currentCard = currentDeck.getFlashcards().get(currentIndex);
        answerLabel.setText(currentCard.getAnswer());
        showAnswerButton.setDisable(true);  // Deaktiver knappen efter visning af svar
    }

    @FXML
    private void handleNextCard() {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        // Hvis der er et "Again"-kort, vis det først
        if (trainingSession.hasPendingAgainCard()) {
            currentIndex = trainingSession.getAgainCardIndex();
            showNextCard();
            return;
        }

        // Ellers fortsæt til næste kort i rækkefølgen
        currentIndex++;
        if (currentIndex >= currentDeck.getFlashcards().size()) {
            currentIndex = 0; // Loop tilbage til starten
        }

        showNextCard();
    }


    public void updateDeckOrder(String answerType) {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("Deck er tomt eller ikke initialiseret.");
            return;
        }

        // Find det nuværende kort og opdater rækkefølgen
        Flashcard currentCard = currentDeck.getFlashcards().get(currentIndex);
        trainingSession.updateDeckOrder(currentCard, answerType);  // Opdater rækkefølgen uden at vise næste kort
    }

    // Denne metode viser det næste kort på skærmen
    private void showNextCard() {
        if (currentIndex < currentDeck.getFlashcards().size()) {
            Flashcard nextCard = currentDeck.getFlashcards().get(currentIndex);
            questionLabel.setText(nextCard.getQuestion());

            String basePath = "C:/Users/Rambo/Documents/Flashcards/greatartists/";
            String imagePath = basePath + nextCard.getImagePath();

            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image("file:" + imagePath);
                questionImage.setImage(image);
            } else {
                questionImage.setImage(null);
            }

            answerLabel.setText("Answer is shown here");
            showAnswerButton.setDisable(false);
        } else {
            System.out.println("Der er ikke flere kort.");
            nextCardButton.setDisable(true); // Deaktiver knappen, hvis der ikke er flere kort
        }
    }
}
