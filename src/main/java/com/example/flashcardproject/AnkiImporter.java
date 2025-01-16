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

    private List<FlashcardDeck> decks;

    public AnkiImporter(String filePath, String imageDirectory) {
        this.filePath = filePath;
        this.imageDirectory = imageDirectory;
        this.decks = new ArrayList<>();
    }

    public List<FlashcardDeck> importFlashcardDecks() throws IOException {
        // Start med at oprette et deck, som vi vil bruge til at tilføje alle flashcards
        FlashcardDeck deck = new FlashcardDeck("Great Works of Art");

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue; // Spring metadata-linjer over

                String[] columns = line.split("\t");

                if (columns.length < 8) {
                    LOGGER.warning("Skipping line - not enough columns: " + line);
                    continue;
                }

                try {
                    String imagePath = extractImagePath(columns[3]); // Billedsti
                    String question = cleanHtmlTags(columns[5]); // Spørgsmål
                    String answer = cleanHtmlTags(columns[4]); // Svar
                    String topic = columns[2].trim(); // Emne

                    // Opret flashcard og tilføj til decket
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

        DeckManager.getInstance().addDeck(deck);

        List<FlashcardDeck> singleDeckList = new ArrayList<>();
        singleDeckList.add(deck);
        return singleDeckList;
    }

    public void importImages() {
        System.out.println("Billeder importeret fra: " + imageDirectory);
    }

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

    public static String cleanHtmlTags(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("<[^>]*>", "").trim();
    }
}
