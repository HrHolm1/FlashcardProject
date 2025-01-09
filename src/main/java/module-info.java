module com.example.flashcardproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.flashcardproject to javafx.fxml;
    exports com.example.flashcardproject;
}