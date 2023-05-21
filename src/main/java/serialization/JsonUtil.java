package serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import game.Bricks;
import structure.modules.LevelContainer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveJsonSaveObj(ArrayList<LevelContainer.SaveObj> data, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), data);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<LevelContainer.loadObj> loadJsonSaveObj(String filename) {
        try {
            return objectMapper.readValue(new File(filename), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, LevelContainer.loadObj.class));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

}

