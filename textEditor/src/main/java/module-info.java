module com.example.texteditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.accessibility;


    opens com.example.texteditor to javafx.fxml;
    exports com.example.texteditor;
}