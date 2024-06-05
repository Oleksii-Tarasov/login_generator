package tek.developer.login_generator.service;

import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tek.developer.login_generator.constant.ResourceFilesPath.UKR_ALPHABET_FILE_PATH;

public class LoginGenerator {
    private static LoginGenerator loginGenerator;
    private final AlphabetLoader alphabetLoader;
    private Map<Character, String> transliterationAlphabet;
    private Map<Character, String> transliterationAlphabetSpecial;

    private LoginGenerator() {
        this.alphabetLoader = new AlphabetLoader();
        this.transliterationAlphabet = new HashMap<>();
        this.transliterationAlphabetSpecial = new HashMap<>();
    }

    public static LoginGenerator getLoginGenerator() {
        if (loginGenerator == null) {
            loginGenerator = new LoginGenerator();
        }

        return loginGenerator;
    }

    public String generateLoginFromFullName(String fullName) {
        fullName = fullName.toUpperCase();
        String[] nameParts = fullName.split(" ");

        if (nameParts.length != 3) {
            return "Full Name must consist of three parts!";
        }

        String name = nameParts[0];
        String lastName = nameParts[1];
        String patronymic = nameParts[2];

        String transliteratedName = transliterateText(name);
        String transliteratedLastName = transliterateText(lastName);
        String transliteratedPatronymic = transliterateText(patronymic);

        String initials = transliteratedName.charAt(0) + transliteratedPatronymic.substring(0, 1);
        transliteratedLastName = transliteratedLastName.charAt(0) + transliteratedLastName.substring(1).toLowerCase();

        return transliteratedLastName + initials;
    }

    public List<FullNameAndLogin> generateLoginsFromFile(Part filePart) throws IOException {
        List<FullNameAndLogin> fullNamesAndLogins = new ArrayList<>();

        if (filePart != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), "UTF-8"))) {
                String fullName;
                while ((fullName = reader.readLine()) != null) {
                    try {
                        String login = generateLoginFromFullName(fullName);
                        fullNamesAndLogins.add(new FullNameAndLogin(fullName, login));
                    } catch (IllegalArgumentException e) {
                        fullNamesAndLogins.add(new FullNameAndLogin(fullName, "Invalid input"));
                    }
                }
            }
        }
        return fullNamesAndLogins;
    }

    private String transliterateText(String text) {
        loadAlphabet();
        StringBuilder transliteratedText = new StringBuilder();

        char firstLetter = text.charAt(0);
        String lastLetters = text.substring(1);

        transliteratedText.append(transliterationAlphabet.getOrDefault(firstLetter, String.valueOf(firstLetter)));

        for (char ch : lastLetters.toCharArray()) {
            if (transliterationAlphabetSpecial.containsKey(ch))
                transliteratedText.append(transliterationAlphabetSpecial.getOrDefault(ch, String.valueOf(ch)));
            else
                transliteratedText.append(transliterationAlphabet.getOrDefault(ch, String.valueOf(ch)));
        }

        return transliteratedText.toString();
    }

    private void loadAlphabet() {
        alphabetLoader.loadAlphabetFromFile(UKR_ALPHABET_FILE_PATH);
        transliterationAlphabet = alphabetLoader.getTransliterationAlphabet();
        transliterationAlphabetSpecial = alphabetLoader.getTransliterationAlphabetSpecial();
    }

    public static class FullNameAndLogin {
        private final String fullName;
        private final String login;

        public FullNameAndLogin(String fullName, String login) {
            this.fullName = fullName;
            this.login = login;
        }

        public String getFullName() {
            return fullName;
        }

        public String getLogin() {
            return login;
        }
    }
}
