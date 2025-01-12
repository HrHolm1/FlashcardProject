package com.example.flashcardproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnkiImporter {

    private static final Logger LOGGER = Logger.getLogger(AnkiImporter.class.getName());

    private String filePath;
    private String imageDirectory;

    // Liste over decks
    private List<FlashcardDeck> decks;

    // Konstruktør
    public AnkiImporter(String filePath, String imageDirectory) {
        this.filePath = filePath;
        this.imageDirectory = imageDirectory;
        this.decks = new ArrayList<>();
    }

    public List<FlashcardDeck> importFlashcardDecks() throws IOException {
        // Start med at oprette et enkelt deck, som vi vil bruge til at tilføje alle flashcards
        FlashcardDeck deck = new FlashcardDeck("Great Works of Art");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue; // Spring metadata-linjer over

                // Split linjen i kolonner
                String[] columns = line.split("\t");

                if (columns.length < 8) {
                    LOGGER.warning("Skipping line - not enough columns: " + line);
                    continue;
                }

                try {
                    // Udtræk relevante data
                    String imagePath = extractImagePath(columns[3]); // Billedsti
                    String question = cleanHtmlTags(columns[5]); // Spørgsmål
                    String answer = cleanHtmlTags(columns[4]); // Svar
                    String topic = columns[2].trim(); // Emne

                    // Opret flashcard og tilføj til det samme deck
                    Flashcard flashcard = new Flashcard(question, answer, imagePath, topic, deck.getFlashcards().size());
                    deck.getFlashcards().add(flashcard);

                    LOGGER.info("Flashcard added to deck 'Great Works of Art': " + flashcard.getQuestion());

                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error processing line: " + line, e);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read the file: " + filePath, e);
            throw e;
        }

        // Returner en liste med ét deck, som indeholder alle flashcards
        List<FlashcardDeck> singleDeckList = new ArrayList<>();
        singleDeckList.add(deck);
        return singleDeckList;
    }

    // Finder et deck baseret på navn eller opretter et nyt, hvis det ikke findes
    private FlashcardDeck findOrCreateDeck(String deckName) {
        for (FlashcardDeck deck : decks) {
            if (deck.getFlashcardDeckName().equalsIgnoreCase(deckName)) {
                return deck;
            }
        }
        // Opret et nyt deck, hvis det ikke findes
        FlashcardDeck newDeck = new FlashcardDeck(deckName);
        newDeck.setFlashcards(new ArrayList<>()); // Initialiser flashcards-listen
        decks.add(newDeck);
        return newDeck;
    }

    // Returner listen af decks
    public List<FlashcardDeck> getDecks() {
        return decks;
    }

    // Metode til at importere billeder fra imageDirectory (kan udvides alt efter behov)
    public void importImages() {
        // Her kan du tilføje logikken til at håndtere billederne, f.eks. ved at kopiere dem til den ønskede mappe
        System.out.println("Billeder importeret fra: " + imageDirectory); // Eksempel på besked
    }

    // Udtræk billedsti fra HTML (eksempel: <img src="path-to-image.jpg">)
    private static String extractImagePath(String html) {
        try {
            String cleanedHtml = html.replace("\"\"", "\"").trim();
            Document doc = Jsoup.parse(cleanedHtml);
            Element img = doc.selectFirst("img");
            if (img != null) {
                return img.attr("src");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error extracting image path from HTML: " + html, e);
        }
        return null;
    }

    // Rens HTML-tags fra tekst
    public static String cleanHtmlTags(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("<[^>]*>", "").trim();
    }
}
