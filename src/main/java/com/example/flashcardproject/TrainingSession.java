package com.example.flashcardproject;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.Queue;

public class TrainingSession {

    private FlashcardDeck currentDeck;
    private int totalCardsLeft;
    private Map<String, Integer> deckProgressMap = new HashMap<>();
    private Queue<Flashcard> flashcardQueue = new LinkedList<>();

    private static TrainingSession instance; // Singleton-instans

    private TrainingSession() {
        // Privat konstruktor for at forhindre direkte instansiering
    }

    public static TrainingSession getInstance() {
        if (instance == null) {
            instance = new TrainingSession();
        }
        return instance;
    }

    public void startSession(String deckName) {
        System.out.println("TrainingSession instance: " + this);
        FlashcardDeck deck = DeckManager.getInstance().getDeckByName(deckName);
        if (deck == null || deck.getFlashcards().isEmpty()) {
            System.err.println("Deck not found or is empty for deck name: " + deckName);
            return;
        }
        this.currentDeck = deck;
        flashcardQueue.clear();
        flashcardQueue.addAll(deck.getFlashcards());
        System.out.println("Session started for deck: " + currentDeck.getFlashcardDeckName());
    }

    public void updateDeckOrder(Flashcard currentCard, String answerType) {
        System.out.println("TrainingSession instance: " + this);
        System.out.println("CurrentDeck: " + (currentDeck != null ? currentDeck.getFlashcardDeckName() : "null"));

        if (currentDeck == null) {
            System.err.println("Error: currentDeck er null i TrainingSession.updateDeckOrder.");
            return; // Stop hvis currentDeck ikke findes
        }

        // Håndtering af forskellige svartyper
        if (answerType.equals("Learned")) {
            removeCardFromDeck(currentCard);
            System.out.println("Card marked as learned and removed from session: " + currentCard.getQuestion());
        } else {
            int originalIndex = currentDeck.getFlashcards().indexOf(currentCard);
            if (originalIndex == -1) {
                System.err.println("Error: Kortet blev ikke fundet i decket.");
                return;
            }

            int newIndex = calculateNewIndex(originalIndex, answerType);
            if (originalIndex != newIndex) {
                currentDeck.getFlashcards().remove(currentCard);
                currentDeck.getFlashcards().add(newIndex, currentCard);
                System.out.println("Card moved to new index: " + newIndex);
            }
            updateIndexes();
        }

        // Opdater køen, efter ændringer er foretaget
        updateFlashcardQueue();
    }

    private void updateFlashcardQueue() {
        // Tøm køen og tilføj alle kortene i den nye rækkefølge
        flashcardQueue.clear();
        flashcardQueue.addAll(currentDeck.getFlashcards());
        System.out.println("Flashcard queue updated: " + flashcardQueue.size() + " cards in queue.");
    }

    private int calculateNewIndex(int originalIndex, String answerType) {
        switch (answerType) {
            case "Hard":
                return Math.max(0, originalIndex + 3);  // Kortet skal vises stort set efter
            case "Medium":
                return Math.max(0, originalIndex + 6);  // Kortet skal vises lidt en smule senere
            case "Easy":
                return Math.min(currentDeck.getFlashcards().size(), originalIndex + 10);  // Kortet skal vises senere
            default:
                return originalIndex;
        }
    }

    private void updateIndexes() {
        for (int i = 0; i < currentDeck.getFlashcards().size(); i++) {
            currentDeck.getFlashcards().get(i).setIndex(i);  // Opdater indexet for hvert kort
        }
    }

    private void removeCardFromDeck(Flashcard card) {
        // Fjern kortet fra både decket og køen
        if (currentDeck.getFlashcards().remove(card)) {
            flashcardQueue.remove(card);  // Fjern det fra køen
            updateIndexes();  // Opdater indekserne efter at kortet er blevet fjernet
        }

        // Efter fjernelse opdater køen baseret på det ændrede deck
        updateFlashcardQueue();
    }

    public Flashcard getNextCard() {
        return flashcardQueue.poll(); // Returner og fjern det første kort i køen (FIFO)
    }

    public void saveProgress(String deckName) {
        // Gem den nuværende position baseret på køens størrelse
        int cardsLeft = flashcardQueue.size();
        deckProgressMap.put(deckName, cardsLeft);
        System.out.println("Progress saved for deck " + deckName + " with " + cardsLeft + " cards left.");
    }

    public int getTotalCardsLeft() {
        return flashcardQueue.size();
    }

    public void recalculateTotalCardsLeft() {
        if (flashcardQueue == null) {
            System.err.println("flashcardQueue er null i recalculateTotalCardsLeft.");
            return;  // Stop metoden, hvis flashcardQueue er null
        }

        // Opdatering af antal kort tilbage baseret på køens størrelse
        this.totalCardsLeft = flashcardQueue.size();  // Brug køens størrelse som det korrekte antal kort
        System.out.println("Total cards left: " + totalCardsLeft);
    }

    public FlashcardDeck getCurrentDeck() {
        return currentDeck;
    }
}
