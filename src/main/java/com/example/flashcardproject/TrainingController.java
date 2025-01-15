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
    private int currentIndex;
    private Flashcard currentCard;

    public TrainingController() {
        trainingSession = new TrainingSession(); // Initialiser TrainingSession
    }

    public void setFlashcardDeck(FlashcardDeck deck) {
        if (deck == null || deck.getFlashcards().isEmpty()) {
            System.err.println("Decket er tomt eller ikke initialiseret!");
            return;
        }
        this.currentDeck = deck;
        trainingSession.startSession(deck);
        currentIndex = 0;  // Sæt indekset korrekt
        showNextCard();  // Vis det første kort
        updateTotalCardsLeftLabel();  // Opdater labelen med antallet af kort
    }

    @FXML
    private void handleShowAnswer() {
        if (currentCard == null) return;

        answerLabel.setText(currentCard.getAnswer());
        showAnswerButton.setDisable(true); // Deaktiver knappen
    }

    @FXML
    private void handleNextCard() {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        // Debug: Tjek currentIndex og antal kort i decket
        System.out.println("Current Index før getNextCard: " + currentIndex);
        System.out.println("Deck størrelse: " + currentDeck.getFlashcards().size());

        // Brug getNextCard til at hente næste kort
        currentCard = trainingSession.getNextCard(currentIndex);

        if (currentCard != null) {
            // Opdater currentIndex baseret på det nye kort
            currentIndex = currentCard.getIndex(); // Opdater currentIndex med det aktuelle korts indeks
            showNextCard();  // Vis det næste kort
            System.out.println("Current Index efter getNextCard: " + currentIndex);  // Debug output
        } else {
            System.out.println("Der er ikke flere kort.");
            nextCardButton.setDisable(true);  // Deaktiver knappen, hvis der ikke er flere kort
        }

        updateTotalCardsLeftLabel();  // Opdater korttællingens label
    }

    public void updateDeckOrder(String answerType) {
        if (currentCard == null) return;

        // Opdater rækkefølgen i TrainingSession
        trainingSession.updateDeckOrder(currentCard, answerType);

        // Vis feedback for, at rækkefølgen blev opdateret
        System.out.println("Rækkefølgen opdateret med svar: " + answerType);
        updateTotalCardsLeftLabel();  // Opdater korttællingens label
    }

    private void showNextCard() {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        // Hent næste kort baseret på currentIndex
        currentCard = currentDeck.getFlashcards().get(currentIndex);

        if (currentCard != null) {
            questionLabel.setText(currentCard.getQuestion());

            String basePath = "C:/Users/Rambo/Documents/Flashcards/greatartists/";  // Opdater denne sti efter behov
            String imagePath = basePath + currentCard.getImagePath();

            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image("file:" + imagePath);
                questionImage.setImage(image);
            } else {
                questionImage.setImage(null);
            }

            answerLabel.setText("Answer is shown here");
            showAnswerButton.setDisable(false);  // Aktiver knappen
            nextCardButton.setDisable(false);  // Sørg for at knappen er aktiveret
        } else {
            System.out.println("Der er ikke flere kort.");
            nextCardButton.setDisable(true); // Deaktiver knappen, hvis der ikke er flere kort
        }
    }

    private void updateTotalCardsLeftLabel() {
        // Opdater totalCardsLeftLabel med antallet af kort tilbage
        trainingSession.recalculateTotalCardsLeft();  // Sørg for at få den nyeste tælling
        totalCardsLeftLabel.setText("Cards Left: " + trainingSession.getTotalCardsLeft());
    }
}