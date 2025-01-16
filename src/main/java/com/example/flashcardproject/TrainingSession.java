package com.example.flashcardproject;

public class TrainingSession {

    private FlashcardDeck currentDeck;
    private int totalCardsLeft; // Antal kort tilbage i decket

    public void startSession(String deckName) {
        FlashcardDeck deck = DeckManager.getInstance().getDeckByName(deckName);
        if (deck == null || deck.getFlashcards().isEmpty()) {
            System.err.println("Deck not found or is empty!");
            return;
        }
        this.currentDeck = deck;
        this.totalCardsLeft = deck.getFlashcards().size();
    }

    public void updateDeckOrder(Flashcard currentCard, String answerType) {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        // Fjern det aktuelle kort fra listen
        int currentIndex = currentCard.getIndex();
        currentDeck.getFlashcards().remove(currentCard);

        // Beregn den nye placering baseret på svar
        int newIndex = 0; // Bruges kun til ikke-"Learned" svar
        switch (answerType) {
            case "Hard":
                newIndex = Math.max(0, currentIndex + 3);
                break;
            case "Medium":
                newIndex = Math.min(currentDeck.getFlashcards().size(), currentIndex + 6);
                break;
            case "Easy":
                newIndex = Math.min(currentDeck.getFlashcards().size(), currentIndex + 10);
                break;
            case "Learned":
                // Kortet fjernes permanent fra decket
                System.out.println("Kortet er markeret som lært og fjernet permanent.");
                break;
            default:
                System.err.println("Ukendt svarmulighed: " + answerType);
                return;
        }

        // Tilføj kortet til den nye position, hvis det ikke er "Learned"
        if (!"Learned".equals(answerType)) {
            currentDeck.getFlashcards().add(newIndex, currentCard);
        }

        // Opdater indekserne på alle kort
        for (int i = 0; i < currentDeck.getFlashcards().size(); i++) {
            Flashcard card = currentDeck.getFlashcards().get(i);
            card.setIndex(i); // Opdater index på hvert kort
        }

        if (!"Learned".equals(answerType)) {
            System.out.println("Kortet er flyttet til indeks: " + newIndex);
        }
    }

    public Flashcard getNextCard(int currentIndex) {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("Decket er tomt eller ikke initialiseret!");
            return null;
        }

        // Debug: Tjek currentIndex og deck størrelse
        System.out.println("Current Index i getNextCard: " + currentIndex);

        // Returner det næste kort i rækkefølgen
        if (currentIndex < currentDeck.getFlashcards().size() - 1) {
            System.out.println("Returnerer næste kort: " + (currentIndex + 1));
            return currentDeck.getFlashcards().get(currentIndex + 1);
        }

        // Hvis vi når slutningen, loop tilbage til starten
        System.out.println("Loop tilbage til start.");
        return currentDeck.getFlashcards().get(0);
    }

    public int getTotalCardsLeft() {
        // Returner antallet af kort tilbage
        return totalCardsLeft;
    }

    public void recalculateTotalCardsLeft() {
        // Sørg for, at totalCardsLeft altid er synkroniseret med deckets faktiske størrelse
        if (currentDeck != null) {
            this.totalCardsLeft = currentDeck.getFlashcards().size();
        } else {
            this.totalCardsLeft = 0;
        }
    }
}