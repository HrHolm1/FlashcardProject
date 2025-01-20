package com.example.flashcardproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private DeckManager deckManager;

    @Override
    public void init() throws Exception {
        super.init();

        // Indlæs data fra fil, når programmet starter
        deckManager = DataMethod.loadDeckManager();
        if (deckManager == null) {
            System.out.println("Ingen gemte data fundet. Opretter ny DeckManager.");
            deckManager = DeckManager.getInstance(); // Opret en ny DeckManager, hvis ingen gemt data findes
        } else {
            // Hvis data blev fundet, brug det indlæste DeckManager
            DeckManager.setInstance(deckManager); // Opret en setInstance-metode i DeckManager, hvis singleton skal bruges
            System.out.println("Data indlæst fra fil.");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("flashcard-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Flashcard");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();

        // Gem data til fil, når programmet lukkes
        DataMethod.saveDeckManager(deckManager);
        System.out.println("DeckManager-data er blevet gemt.");
    }

    public static void main(String[] args) {
        launch(); // Start JavaFX-applikationen
    }
}