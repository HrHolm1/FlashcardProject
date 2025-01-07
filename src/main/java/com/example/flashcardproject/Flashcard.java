package com.example.flashcardproject;

public class Flashcard {

    private String question; // Shows the question tied to the card.

    private String answer; // Shows the answer for the given card.

    private String imagePath; // Each flashcard has an image tied to it, this is that path.

    private String topic; // Each flashcard has a certain topic tied it.

    private boolean isIrrelevant; // Allows the user to mark a card as irrelevant.

    private int reviewScore; // 0 points if incorrect, 1 point if partially correct etc.

    private int index; // Gives each individual card an index used for queue system.

    public Flashcard(String question, String answer, String imagePath, String topic, int index, int reviewScore) {
        this.question = question;
        this.answer = answer;
        this.imagePath = imagePath;
        this.topic = topic;
        this.index = index;
        this.reviewScore = reviewScore;
        this.isIrrelevant = false;
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public String getImagePath(){
        return imagePath;
    }

    public void setImagePath(String imagePath){
        this.imagePath = imagePath;
    }

    public String getTopic(){
        return topic;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }

    public int getIndex(){
        return index;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public int getReviewScore(){
        return reviewScore;
    }

    public void setReviewScore(int reviewScore){
        this.reviewScore = reviewScore;
    }

    public void markAsIrrelevant(){
        this.isIrrelevant = true;
    }

}
