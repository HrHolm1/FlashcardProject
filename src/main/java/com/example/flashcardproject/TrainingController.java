package com.example.flashcardproject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

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

    private List<Flashcard> flashcards;  // Listen af flashcards
    private int currentIndex = 0;        // Index til at holde styr på det aktuelle kort

    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        showFlashcard(currentIndex);  // Vis det første kort
    }

    @FXML
    private void handleShowAnswer() {
        if (flashcards == null || flashcards.isEmpty()) {
            System.err.println("Flashcards listen er tom eller ikke initialiseret!");
            return; // Forhindrer, at der opstår en NullPointerException
        }

        Flashcard currentCard = flashcards.get(currentIndex);
        answerLabel.setText(currentCard.getAnswer());  // Vist kunstnerens navn
        showAnswerButton.setDisable(true);
    }

    @FXML
    private void handleNextCard() {
        if (flashcards == null || flashcards.isEmpty()) {
            System.err.println("Flashcards listen er tom eller ikke initialiseret!");
            return; // Forhindrer, at der opstår en NullPointerException
        }

        if (currentIndex < flashcards.size() - 1) {
            currentIndex++;
            showFlashcard(currentIndex);
        } else {
            System.out.println("Der er ikke flere kort.");
        }
    }

    private void showFlashcard(int index) {
        if (flashcards == null || flashcards.isEmpty()) {
            System.err.println("Flashcards listen er tom eller ikke initialiseret!");
            return; // Forhindrer fejl
        }

        Flashcard currentCard = flashcards.get(index);
        questionLabel.setText(currentCard.getQuestion());  // Vist maleriets titel

        String basePath = "C:/Users/Rambo/Documents/Flashcards/greatartists/";  // Tilpas denne sti
        String imagePath = basePath + currentCard.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image("file:" + imagePath);
            questionImage.setImage(image);
            System.out.println("image path: " + imagePath);
        } else {
            questionImage.setImage(null);
            System.out.println("image path is null");
        }

        answerLabel.setText("Answer is shown here");
        showAnswerButton.setDisable(false);
    }
}
