package com.example.flashcardproject;

public class TrainingSession {

    private FlashcardDeck currentDeck;
    private Flashcard lastAgainCard;  // Variabel til at gemme det kort, der blev valgt til "Again"

    // Starter sessionen med det deck, som brugeren har importeret
    public void startSession(FlashcardDeck deck) {
        if (deck == null || deck.getFlashcards().isEmpty()) {
            System.err.println("Decket er tomt eller ikke initialiseret!");
            return;
        }
        this.currentDeck = deck;
    }

    private int againCardIndex = -1; // Husk indekset for det sidst valgte "Again"-kort

    public void updateDeckOrder(Flashcard currentCard, String answerType) {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("FlashcardDeck er tomt eller ikke initialiseret!");
            return;
        }

        int currentIndex = currentCard.getIndex();
        currentDeck.getFlashcards().remove(currentCard);

        int newIndex = -1;
        switch (answerType) {
            case "Again":
                // "Again" kortet skal vises først, så flyt det til starten
                newIndex = 0;
                againCardIndex = newIndex; // Husk det som "Again"-kort
                break;

            case "Hard":
                // "Hard" kortet skal vises hurtigere, så flyt det 3 pladser frem
                newIndex = Math.max(0, currentIndex + 3);
                break;

            case "Medium":
                // "Medium" kortet skal vises lidt senere, så flyt det 6 pladser frem
                newIndex = Math.min(currentDeck.getFlashcards().size(), currentIndex + 6);
                break;

            case "Easy":
                // "Easy" kortet skal vises senere, så flyt det 10 pladser frem
                newIndex = Math.min(currentDeck.getFlashcards().size(), currentIndex + 10);
                break;

            default:
                System.err.println("Ukendt svarmulighed: " + answerType);
                return;
        }

        // Tilføj kortet til den nye position i decket
        currentDeck.getFlashcards().add(newIndex, currentCard);

        // Opdater indekserne for alle kort
        for (int i = 0; i < currentDeck.getFlashcards().size(); i++) {
            currentDeck.getFlashcards().get(i).setIndex(i);
        }

        System.out.println("Kortet er flyttet til indeks: " + newIndex);
    }

    public boolean hasPendingAgainCard() {
        return againCardIndex != -1;
    }

    public int getAgainCardIndex() {
        return againCardIndex;
    }


    public Flashcard getNextCard() {
        if (currentDeck == null || currentDeck.getFlashcards().isEmpty()) {
            System.err.println("Decket er tomt eller ikke initialiseret!");
            return null;
        }

        // Find kortet med det mindste indeks, der ikke er blevet gennemgået
        for (Flashcard card : currentDeck.getFlashcards()) {
            if (card.getIndex() > -1) { // Check at kortet stadig har et gyldigt indeks
                return card;
            }
        }

        System.out.println("Der er ikke flere kort.");
        return null;
    }

}
