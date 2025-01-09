package com.example.flashcardproject;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnkiImporter {

    private String textFilePath;
    private String imageFolderPath;

    // Constructor
    public AnkiImporter(String textFilePath, String imageFolderPath) {
        this.textFilePath = textFilePath;
        this.imageFolderPath = imageFolderPath;
    }

    // Import flashcards fra tekstfil
    public List<Flashcard> importFlashcards() throws IOException {
        List<Flashcard> flashcards = new ArrayList<>();
        Scanner scanner = new Scanner(new File(textFilePath));

        int lineNumber = 0; // Tilføjer linjenummer for debugging
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineNumber++;

            // Ignorer metadata-linjer, der starter med '#'
            if (line.startsWith("#") || line.trim().isEmpty()) {
                System.out.println("Metadata eller tom linje ignoreret på linje " + lineNumber + ": " + line);
                continue;
            }

            // Split linjen på tabulator ('\t')
            String[] fields = line.split("\t");

            // Kontrollér, at linjen har nok felter
            if (fields.length >= 4) { // Mindst 4 felter (fx: id, kategori, billede, kunstner)
                String id = fields[0]; // ID på kortet
                String category = fields[1]; // Kategori
                String deck = fields[2]; // Bunke
                String imageHTML = fields[3]; // Billede som HTML

                // (Optional) Ekstra felter
                String artist = fields.length > 4 ? fields[4] : "";
                String title = fields.length > 5 ? fields[5] : "";
                String year = fields.length > 6 ? fields[6] : "";
                String style = fields.length > 7 ? fields[7] : "";
                String region = fields.length > 8 ? fields[8] : "";

                // Udtræk billedsti fra HTML-tagget
                String imagePath = null;
                if (imageHTML.contains("src=\"")) {
                    int startIndex = imageHTML.indexOf("src=\"") + 5;
                    int endIndex = imageHTML.indexOf("\"", startIndex);
                    if (startIndex > 0 && endIndex > startIndex) {
                        imagePath = imageHTML.substring(startIndex, endIndex);
                    }
                }

                // Tilføj et nyt flashcard til listen
                Flashcard flashcard = new Flashcard(title, artist, imagePath, category, flashcards.size());
                flashcards.add(flashcard);

            } else {
                // Linjen har ikke nok felter - log en fejl
                System.err.println("Ugyldig linje på linje " + lineNumber + " (ikke nok felter): " + line);
            }
        }

        scanner.close();
        return flashcards;
    }

    // Import billederne
    public void importImages() throws IOException {
        File imageFolder = new File(imageFolderPath);

        // Hvis mappen ikke eksisterer, lav den
        if (!imageFolder.exists()) {
            imageFolder.mkdir();
        }

        // Eksempel på at finde og kopiere billeder
        File[] imageFiles = new File(textFilePath).listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));

        if (imageFiles != null) {
            for (File image : imageFiles) {
                Files.copy(image.toPath(), Paths.get(imageFolderPath, image.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
}
