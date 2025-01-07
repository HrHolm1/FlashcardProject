package com.example.flashcardproject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

public class FlashcardController {

    private Statistics statistics = new Statistics();

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


}