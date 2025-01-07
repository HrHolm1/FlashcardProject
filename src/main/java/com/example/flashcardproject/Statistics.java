package com.example.flashcardproject;

public class Statistics {

    private int correctAnswersCount; // Counter for correct answers, corresponds to the "Easy" button.

    private int partiallyCorrectAnswersCount; // Counter for partially correct answers, corresponds to the "Medium" button.

    private int almostCorrectAnswersCount; // Counter for almost correct answers, corresponds to the "Hard" button.

    private int incorrectAnswersCount; // Counter for incorrect answers, corresponds to the "Again" button.

    private int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    private void setCorrectAnswersCount(int correctAnswersCount){
        this.correctAnswersCount = correctAnswersCount;
    }

    private int getPartiallyCorrectAnswersCount() {
        return partiallyCorrectAnswersCount;
    }

    private void setPartiallyCorrectAnswersCount(int partiallyCorrectAnswersCount){
        this.partiallyCorrectAnswersCount = partiallyCorrectAnswersCount;
    }

    private int getAlmostCorrectAnswersCount() {
        return almostCorrectAnswersCount;
    }

    private void setAlmostCorrectAnswersCount(int almostCorrectAnswersCount){
        this.almostCorrectAnswersCount = almostCorrectAnswersCount;
    }

    private int getIncorrectAnswersCount() {
        return incorrectAnswersCount;
    }

    private void setIncorrectAnswersCount(int incorrectAnswersCount){
        this.incorrectAnswersCount = incorrectAnswersCount;
    }

    public void answerStatistics(String answerOption) {
        switch (answerOption.toLowerCase()) {
            case "easy":
                correctAnswersCount++;
                break;
            case "medium":
                partiallyCorrectAnswersCount++;
                break;
            case "hard":
                almostCorrectAnswersCount++;
                break;
            case "again":
                incorrectAnswersCount++;
                break;
            default:
                System.out.println("Invalid answer option: " + answerOption);
        }
    }


}
