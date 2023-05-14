package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveJson(Bricks data, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), data);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Object loadJson(String filename) {
        try {
            return objectMapper.readValue(new File(filename), objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Bricks.BrickData.class));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    //----------------------------------------------------------------

    public void saveJsonObj(Obj obj, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), obj);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Obj loadJsonObj(String fileName) {
        try {
            return objectMapper.readValue(new File(fileName), Obj.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
