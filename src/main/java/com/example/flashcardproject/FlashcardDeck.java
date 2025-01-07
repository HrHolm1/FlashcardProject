package com.example.flashcardproject;

import java.util.ArrayList;

public class FlashcardDeck {

    private String flashcardDeckName;

    private ArrayList<Flashcard> flashcards;

    public FlashcardDeck(String flashcardDeckName) {
        this.flashcardDeckName = flashcardDeckName;
    }

    public String getFlashcardDeckName() {
        return flashcardDeckName;
    }

    public void setFlashcardDeckName(String flashcardDeckName){
        this.flashcardDeckName = flashcardDeckName;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }


    private void addFlashcard(Flashcard flashcard){
        flashcards.add(flashcard);
    }

    private void removeFlashcard(Flashcard flashcard){
        flashcards.remove(flashcard);
    }

    private void filterFlashcards(String topic) {
        ArrayList<Flashcard> filteredFlashcards = new ArrayList<>();

        // Iterate through the current list and add matching flashcards to the new list
        for (Flashcard flashcard : flashcards) {
            if (flashcard.getTopic().equalsIgnoreCase(topic)) {
                filteredFlashcards.add(flashcard);
            }
        }

        // Replace the original list with the filtered list
        this.flashcards = filteredFlashcards;
    }

}
