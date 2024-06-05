package tek.developer.login_generator.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AlphabetLoader {
    private final ObjectMapper objectMapper= new ObjectMapper();
    private final Map<Character, String> transliterationAlphabet = new HashMap<>();
    private final Map<Character, String> transliterationAlphabetSpecial = new HashMap<>();

    public void loadAlphabetFromFile(String filePath) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)){
            if (inputStream == null) {
                throw new RuntimeException("Alphabet not found: " + filePath);
            }

            Map<String, Map<String, String>> mappings = objectMapper.readValue(inputStream, Map.class);
            mappings.get("default").forEach((key, value) -> transliterationAlphabet.put(key.charAt(0), value));
            mappings.get("special").forEach((key, value) -> transliterationAlphabetSpecial.put(key.charAt(0), value));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load transliteration mappings from JSON file: " + filePath, e);
        }
    }

    public Map<Character, String> getTransliterationAlphabet() {
        return transliterationAlphabet;
    }

    public Map<Character, String> getTransliterationAlphabetSpecial() {
        return transliterationAlphabetSpecial;
    }
}
