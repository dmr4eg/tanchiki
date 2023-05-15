module game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires java.logging;

//    exports controller;
//    exports view;
    exports game; // add this line to export the model package
     // add this line to open the model package to javafx.base
    opens game to com.fasterxml.jackson.databind;
    exports net to javafx.graphics;

//    opens view to javafx.fxml;
//    opens controller to javafx.fxml;

}