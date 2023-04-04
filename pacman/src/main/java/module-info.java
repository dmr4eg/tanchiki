module game.pacman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

//    exports controller;
//    exports view;
    exports model; // add this line to export the model package
    opens model to javafx.base; // add this line to open the model package to javafx.base
//    opens view to javafx.fxml;
//    opens controller to javafx.fxml;

}