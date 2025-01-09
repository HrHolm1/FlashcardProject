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

        // Indstil tab som delimiter
        scanner.useDelimiter("\t|\r\n|\n");

        int index = 0;  // Start index til flashcards

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] fields = line.split("\t");

            // Validering af antal felter
            if (fields.length < 2) continue; // Spring linjer over, hvis der ikke er spørgsmål/svar

            String question = fields[0];
            String answer = fields[1];
            String topic = (fields.length > 3) ? fields[3] : "General"; // Default topic
            String imageName = (fields.length > 2) ? fields[2] : null;

            // Konstruer billedsti, hvis relevant
            String imagePath = (imageName != null && !imageName.isEmpty()) ?
                    imageFolderPath + File.separator + imageName : null;

            // Opret Flashcard med index
            Flashcard flashcard = new Flashcard(question, answer, imagePath, topic, index);
            flashcards.add(flashcard);

            index++; // Opdater index for næste flashcard
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
