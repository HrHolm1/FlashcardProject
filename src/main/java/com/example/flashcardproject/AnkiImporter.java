package com.example.flashcardproject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnkiImporter {

    private static final Logger LOGGER = Logger.getLogger(AnkiImporter.class.getName());

    private String filePath;
    private String imageDirectory;

    // Konstruktør der modtager både filePath og imageDirectory
    public AnkiImporter(String filePath, String imageDirectory) {
        this.filePath = filePath;
        this.imageDirectory = imageDirectory;
    }

    // Metode til at importere flashcards fra tekstfilen
    public List<Flashcard> importFlashcards() throws IOException {
        List<Flashcard> flashcards = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skipper metadata linjer
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) continue;

                // Splitter linjen op i kolonner
                String[] columns = line.split("\t");

                // Tjekker at der er nok kolonner
                if (columns.length < 7) {
                    LOGGER.warning("Skipping line - not enough columns: " + line);
                    continue;
                }

                try {
                    // Henter data fra filen
                    String imagePath = extractImagePath(columns[3]);  // Eksempel på billedsti
                    String question = cleanHtmlTags(columns[5]);  // Tydeligvis maleriets titel
                    String answer = cleanHtmlTags(columns[4]);  // Kunstnerens navn
                    String topic = columns[2];  // Emnet for kortet
                    int index = flashcards.size(); // Brug størrelsen på listen som indeks

                    // Opretter flashcard objekt
                    Flashcard flashcard = new Flashcard(question, answer, imagePath, topic, index);

                    flashcards.add(flashcard);

                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error reading line: " + line, e);
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to read the file: " + filePath, e);
            throw new RuntimeException(e);
        }

        return flashcards;
    }


    // Metode til at importere billeder fra imageDirectory (kan udvides alt efter behov)
    public void importImages() {
        // Her kan du tilføje logikken til at håndtere billederne, f.eks. ved at kopiere dem til den ønskede mappe
        System.out.println("Billeder importeret fra: " + imageDirectory); // Eksempel på besked
    }

    // Hjælper til at udtrække billedstien fra HTML (f.eks. <img src="path-to-image.jpg">)
    private static String extractImagePath(String html) {
        try {
            String cleanedHtml = html.replace("\"\"", "\"").trim(); // Renser ekstra citationstegn
            Document doc = Jsoup.parse(cleanedHtml);
            Element img = doc.selectFirst("img");  // Finder den første <img> tag
            if (img != null) {
                return img.attr("src"); // Returnerer billedsti
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error extracting image path from HTML: " + html, e);
        }
        return null; // Returnerer null hvis ingen billede findes
    }

    private String extractImageTitle(String html) {
        try {
            // Ryd op i ekstra citationstegn og trim whitespace
            String cleanedHtml = html.replace("\"\"", "\"").trim();  // Erstat "" med "

            // Parse den rensede HTML
            Document doc = Jsoup.parse(cleanedHtml);

            // Hent <img> elementet
            Element img = doc.selectFirst("img");
            if (img != null) {
                // Returner billedets filnavn eller alt tekst som spørgsmål
                String imageTitle = img.attr("alt");  // Du kan vælge at bruge filnavnet også (img.attr("src"))
                if (imageTitle.isEmpty()) {
                    // Hvis alt-teksten ikke er tilgængelig, brug filnavnet som spørgsmål
                    imageTitle = img.attr("src");
                }
                return imageTitle;  // Returner den fundne titel
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error extracting image title from HTML: " + html, e);
        }
        return "";  // Hvis der ikke findes en titel, returner en tom streng
    }

    // Hjælper til at rense HTML tags fra tekst
    public static String cleanHtmlTags(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("<[^>]*>", "").trim(); // Fjerner alle HTML tags
    }
}