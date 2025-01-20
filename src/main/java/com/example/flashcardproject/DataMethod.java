package com.example.flashcardproject;

import java.io.*;

public class DataMethod {

    // Definer placeringen som en undermappe i Dokumenter
    private static final String DIRECTORY = System.getProperty("user.home") + "/Documents/FlashcardData";
    private static final String FILE_NAME = DIRECTORY + "/deckmanager_data.ser";

    // Gem DeckManager til en fil i Dokumenter
    public static void saveDeckManager(DeckManager deckManager) {
        try {
            // Opret mappen, hvis den ikke allerede findes
            File directory = new File(DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs(); // Opret mappen og dens forældre, hvis nødvendigt
                System.out.println("Mappen '" + DIRECTORY + "' blev oprettet.");
            }

            // Gem datafilen
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
                oos.writeObject(deckManager); // Serialiser DeckManager-objektet
                System.out.println("DeckManager-data er gemt til: " + FILE_NAME);
            }
        } catch (IOException e) {
            System.err.println("Fejl under gemning: " + e.getMessage());
        }
    }

    // Indlæs DeckManager fra filen i Dokumenter
    public static DeckManager loadDeckManager() {
        try {
            File file = new File(FILE_NAME); // Find datafilen
            if (!file.exists()) {
                System.out.println("Datafilen findes ikke. Der vil blive oprettet en ny.");
                return null; // Returner null, hvis filen ikke findes
            }

            // Læs datafilen
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (DeckManager) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Fejl under indlæsning: " + e.getMessage());
        }
        return null; // Returner null, hvis der opstod en fejl
    }
}