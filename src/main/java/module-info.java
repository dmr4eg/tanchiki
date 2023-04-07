module tanchiki.assyldamnurakali {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens tanchiki.assyldamnurakali to javafx.base;
    exports tanchiki.assyldamnurakali;
}