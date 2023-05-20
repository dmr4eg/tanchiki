package frontend;

import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuItem extends StackPane {
    private Rectangle line;
    Text text = new Text();
    public MenuItem(String name, Runnable action, String type){
        LinearGradient gradient = new LinearGradient(
                0, 0.5, 1, 0.5, true, CycleMethod.NO_CYCLE,
                new Stop(0.1, Color.web("black", 0.75)),
                new Stop(1.0, Color.web("black", 0.15))
        );
        Rectangle bg;
        if(type.equals("menu")) {
            bg = new Rectangle(800, 140, gradient);
            line = new Rectangle(5, 30);
        } else if (type.equals("levelEditor")) {
            bg = new Rectangle(150, 30, gradient);
            line = new Rectangle(5, 30);
        }else{
            bg = new Rectangle(800, 93, gradient);
            line = new Rectangle(5, 30);
        }
        if(type.equals("levelEditor")){
            line.fillProperty().bind(
                    Bindings.when(hoverProperty()).then(Color.RED).otherwise(Color.GRAY));
        }
        text.setText(name);
        text.fillProperty().bind(
                Bindings.when(hoverProperty()).then(Color.WHITE).otherwise(Color.GRAY)
        );
        Font font;
        font = Font.loadFont(getClass().getResourceAsStream("/CCOverbyteOffW00-Regular.ttf"), 30);

        text.setFont(font);

        setOnMouseClicked(e -> action.run());
        setAlignment(Pos.CENTER);

        HBox box = new HBox(20, line, text);
        box.setAlignment(Pos.CENTER);
        if(type.equals("levelEditor")){
            setAlignment(Pos.BASELINE_LEFT);
            box = new HBox(10, line, text);
            box.setAlignment(Pos.BASELINE_LEFT);
        }
        getChildren().addAll(bg, box);
    }

    public void setOn(){
        line.setFill(Color.RED);
        text.setFill(Color.WHITE);
    }
}
