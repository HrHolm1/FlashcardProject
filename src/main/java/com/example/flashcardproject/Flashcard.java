package com.example.flashcardproject;

public class Flashcard {
    private String question; // Titel på maleriet (spørgsmålet)
    private String answer;   // Kunstnerens navn (svaret)
    private String imagePath; // Stien til billedet af maleriet
    private String topic;     // Emnet for kortet
    private int index;        // Indeks for kortet (bruges til at styre rækkefølge)

    public Flashcard(String question, String answer, String imagePath, String topic, int index) {
        this.question = question;
        this.answer = answer;
        this.imagePath = imagePath;
        this.topic = topic;
        this.index = index;
    }

    // Getters og setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}