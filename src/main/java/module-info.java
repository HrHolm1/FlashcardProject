module com.example.flashcardproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.flashcardproject to javafx.fxml;
    exports com.example.flashcardproject;
}