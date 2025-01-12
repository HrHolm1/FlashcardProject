package com.example.flashcardproject;

import java.util.ArrayList;

public class FlashcardDeck {

    private String flashcardDeckName;
    private ArrayList<Flashcard> flashcards;

    // Konstruktør, initialiser flashcards listen
    public FlashcardDeck(String flashcardDeckName) {
        this.flashcardDeckName = flashcardDeckName;
        this.flashcards = new ArrayList<>();  // Initialiser listen
    }

    public String getFlashcardDeckName() {
        return flashcardDeckName;
    }

    public void setFlashcardDeckName(String flashcardDeckName) {
        this.flashcardDeckName = flashcardDeckName;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;  // Returner den initialiserede liste
    }

    public void setFlashcards(ArrayList<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }

    // Metode til at tilføje et flashcard
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    // Metode til at fjerne et flashcard
    public void removeFlashcard(Flashcard flashcard) {
        flashcards.remove(flashcard);
    }

    // Metode til at filtrere flashcards baseret på emne
    public void filterFlashcards(String topic) {
        ArrayList<Flashcard> filteredFlashcards = new ArrayList<>();

        // Iterer gennem den nuværende liste og tilføj de matchende flashcards
        for (Flashcard flashcard : flashcards) {
            if (flashcard.getTopic().equalsIgnoreCase(topic)) {
                filteredFlashcards.add(flashcard);
            }
        }

        // Erstat den oprindelige liste med den filtrerede liste
        this.flashcards = filteredFlashcards;
    }
}