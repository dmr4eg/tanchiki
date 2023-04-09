package game;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;

public class GameModel extends Application {

    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private Bricks brickData;

    public void start(Stage stage) throws IOException {
        String fileName = "game/brrr.json";

        FileReader fileReader = new FileReader(fileName);

    }
}
//
//        // Read brick positions from JSON file
//        Gson gson = new GsonBuilder().create();
//        FileReader reader = new FileReader("brrr.json");
//        brickData = gson.fromJson(reader, Bricks.class);
//
//        // Create group to hold all bricks
//        Group root = new Group();
//
//        // Add solid bricks to group
//        for (int i = 0; i < brickData.solidBricksXPos.length; i++) {
//            int x = brickData.solidBricksXPos[i];
//            int y = brickData.solidBricksYPos[i];
//            Rectangle brick = new Rectangle(x, y, 50, 50);
//            brick.setFill(Color.BLUE);
//            root.getChildren().add(brick);
//        }
//
//        // Add breakable bricks to group
//        for (int i = 0; i < brickData.bricksXPos.length; i++) {
//            int x = brickData.bricksXPos[i];
//            int y = brickData.bricksYPos[i];
//            Rectangle brick = new Rectangle(x, y, 50, 50);
//            brick.setFill(Color.GREEN);
//            root.getChildren().add(brick);
//        }
//
//        // Set up scene and show stage
//        Scene scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
