package com.example.flashcardproject;

import java.util.ArrayList;
import java.util.List;

public class DeckManager {
    private static DeckManager instance; // Singleton instans
    private List<FlashcardDeck> decks;

    private DeckManager() {
        decks = new ArrayList<>();
    }

    public static DeckManager getInstance() {
        if (instance == null) {
            instance = new DeckManager();
        }
        return instance;
    }

    public void addDeck(FlashcardDeck deck) {
        decks.add(deck);
    }

    public List<FlashcardDeck> getDecks() {
        return decks;
    }

    public FlashcardDeck getDeckByName(String name) {
        for (FlashcardDeck deck : decks) {
            if (deck.getFlashcardDeckName().equalsIgnoreCase(name)) {
                return deck;
            }
        }
        return null; // Hvis deck ikke findes
    }
}