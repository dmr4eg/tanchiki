module game.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;

//    exports controller;
//    exports view;
    exports game; // add this line to export the model package
    opens game to javafx.base; // add this line to open the model package to javafx.base
//    opens view to javafx.fxml;
//    opens controller to javafx.fxml;

}