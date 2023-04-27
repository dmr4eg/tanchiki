package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveJson(DataProcessing data, String fileName) {
        try {
            objectMapper.writeValue(new File(fileName), data);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public DataProcessing loadJson(String fileName) {
        try {
            return objectMapper.readValue(new File(fileName), DataProcessing.class);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
