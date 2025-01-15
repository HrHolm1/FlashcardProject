package com.example.flashcardproject;

public class Statistics {

    private int correctAnswersCount; // Counter for correct answers, corresponds to the "Easy" button.

    private int partiallyCorrectAnswersCount; // Counter for partially correct answers, corresponds to the "Medium" button.

    private int almostCorrectAnswersCount; // Counter for almost correct answers, corresponds to the "Hard" button.

    private int learnedAnswersCount; // Counter for incorrect answers, corresponds to the "Again" button.

    int getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    private void setCorrectAnswersCount(int correctAnswersCount){
        this.correctAnswersCount = correctAnswersCount;
    }

    int getPartiallyCorrectAnswersCount() {
        return partiallyCorrectAnswersCount;
    }

    private void setPartiallyCorrectAnswersCount(int partiallyCorrectAnswersCount){
        this.partiallyCorrectAnswersCount = partiallyCorrectAnswersCount;
    }

    int getAlmostCorrectAnswersCount() {
        return almostCorrectAnswersCount;
    }

    private void setAlmostCorrectAnswersCount(int almostCorrectAnswersCount){
        this.almostCorrectAnswersCount = almostCorrectAnswersCount;
    }

    int getLearnedAnswersCount() {
        return learnedAnswersCount;
    }

    private void learnedAnswersCount(int incorrectAnswersCount){
        this.learnedAnswersCount = incorrectAnswersCount;
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
            case "learned":
                learnedAnswersCount++;
                break;
            default:
                System.out.println("Invalid answer option: " + answerOption);
        }
    }


}
