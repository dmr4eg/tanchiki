module game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

//    exports controller;
//    exports view;
    exports game; // add this line to export the model package
     // add this line to open the model package to javafx.base
    opens game to com.fasterxml.jackson.databind;
//    opens view to javafx.fxml;
//    opens controller to javafx.fxml;

}