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
    @FXML
    private Label totalCardsLeftLabel;

    private FlashcardDeck currentDeck;
    private TrainingSession trainingSession;
    private Flashcard currentCard;

    public TrainingController() {
        // Brug den samme singleton-instans
        this.trainingSession = TrainingSession.getInstance();
    }

    public void setTrainingSession(TrainingSession trainingSession) {
        this.trainingSession = trainingSession;
        System.err.println("trainingSession er allerede sat, overskriver ikke.");
    }

    public void setFlashcardDeck(FlashcardDeck deck) {
        if (deck == null) {
            throw new IllegalArgumentException("Deck cannot be null");
        }

        this.currentDeck = deck;
        System.out.println("currentDeck blev sat til: " + deck.getFlashcardDeckName());

        if (trainingSession != null) {
            trainingSession.startSession(deck.getFlashcardDeckName());
            currentCard = trainingSession.getNextCard();
            showNextCard();
            updateTotalCardsLeftLabel();
        }
    }

    @FXML
    private void handleShowAnswer() {
        if (currentCard != null) {
            answerLabel.setText(currentCard.getAnswer());
            showAnswerButton.setDisable(true);
        }
    }

    @FXML
    private void handleNextCard() {
        // Hent det næste kort fra køen
        currentCard = trainingSession.getNextCard();

        if (currentCard != null) {
            showNextCard();
        } else {
            nextCardButton.setDisable(true); // Deaktiver knappen, hvis der ikke er flere kort
        }

        // Gem progress i sessionen for decket
        trainingSession.saveProgress(currentDeck.getFlashcardDeckName());
        updateTotalCardsLeftLabel();  // Opdater label for antal kort tilbage
    }

    private void showNextCard() {
        questionLabel.setText(currentCard.getQuestion());
        Image image = new Image("file:" + currentCard.getFullImagePath());
        questionImage.setImage(image);
        answerLabel.setText("Answer is shown here");
        showAnswerButton.setDisable(false);
        nextCardButton.setDisable(false);
    }

    public void updateDeckOrder(String answerType) {
        // Hent altid currentDeck fra TrainingSession
        currentDeck = trainingSession.getCurrentDeck();

        if (currentDeck == null) {
            System.err.println("currentDeck er null i updateDeckOrder efter hentning fra TrainingSession.");
            return; // Stop metoden, hvis currentDeck stadig er null
        }

        System.out.println("Opdaterer currentDeck for answerType " + answerType + ": " + currentDeck.getFlashcardDeckName());

        // Opdater rækkefølgen via trainingSession
        trainingSession.updateDeckOrder(currentCard, answerType);
        currentCard = trainingSession.getNextCard(); // Få næste kort
        updateTotalCardsLeftLabel(); // Opdatér antal kort tilbage
    }

    private void updateTotalCardsLeftLabel() {
        trainingSession.recalculateTotalCardsLeft();
        totalCardsLeftLabel.setText("Cards Left: " + trainingSession.getTotalCardsLeft());
    }

    public FlashcardDeck getCurrentDeck() {
        return currentDeck;  // Returnerer currentDeck for at sikre, at jeg kan kontrollere det udenfor
    }

}