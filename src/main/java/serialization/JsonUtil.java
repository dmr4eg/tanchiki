package serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import structure.modules.LevelContainer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class JsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = Logger.getLogger(JsonUtil.class.getName());

    public void saveJsonSaveObj(ArrayList<LevelContainer.SaveObj> data, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), data);
            LOGGER.info("Successfully saved JSON data to file: " + fileName);
        } catch (IOException ex) {
            LOGGER.severe("Error saving JSON data to file: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<LevelContainer.loadObj> loadJsonSaveObj(String filename) {
        try {
            LOGGER.info("Successfully loaded JSON data from file: " + filename);
            return objectMapper.readValue(new File(filename), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, LevelContainer.loadObj.class));
        } catch (IOException ex) {
            LOGGER.severe("Error loading JSON data from file: " + ex.getMessage());
            System.out.println(ex.getMessage());
            return null;
        }
    }
}