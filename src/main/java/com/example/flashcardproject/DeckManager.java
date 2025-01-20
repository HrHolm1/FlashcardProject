package com.example.flashcardproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeckManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private static DeckManager instance; // Singleton instans
    private List<FlashcardDeck> decks;

    private DeckManager() {
        decks = new ArrayList<>();
    }

    public static void setInstance(DeckManager loadedInstance) {
        instance = loadedInstance;
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
                System.out.println("Found deck: " + name);  // Log for succesfuldt fundet deck
                return deck;
            }
        }
        System.err.println("Deck not found: " + name);  // Log hvis deck ikke findes
        return null;
    }
}