package tek.developer.login_generator.service;

import java.util.HashMap;
import java.util.Map;

public class Generator {
    private static final Map<Character, String> TRANSLITERATION_UKR_ALPHABET = new HashMap<>();

    static {
        TRANSLITERATION_UKR_ALPHABET.put('А', "A"); TRANSLITERATION_UKR_ALPHABET.put('Б', "B"); TRANSLITERATION_UKR_ALPHABET.put('В', "V");
        TRANSLITERATION_UKR_ALPHABET.put('Г', "H"); TRANSLITERATION_UKR_ALPHABET.put('Ґ', "G"); TRANSLITERATION_UKR_ALPHABET.put('Д', "D");
        TRANSLITERATION_UKR_ALPHABET.put('Е', "E"); TRANSLITERATION_UKR_ALPHABET.put('Є', "Ye"); TRANSLITERATION_UKR_ALPHABET.put('Ж', "Zh");
        TRANSLITERATION_UKR_ALPHABET.put('З', "Z"); TRANSLITERATION_UKR_ALPHABET.put('И', "Y"); TRANSLITERATION_UKR_ALPHABET.put('І', "I");
        TRANSLITERATION_UKR_ALPHABET.put('Ї', "Yi"); TRANSLITERATION_UKR_ALPHABET.put('Й', "Y"); TRANSLITERATION_UKR_ALPHABET.put('К', "K");
        TRANSLITERATION_UKR_ALPHABET.put('Л', "L"); TRANSLITERATION_UKR_ALPHABET.put('М', "M"); TRANSLITERATION_UKR_ALPHABET.put('Н', "N");
        TRANSLITERATION_UKR_ALPHABET.put('О', "O"); TRANSLITERATION_UKR_ALPHABET.put('П', "P"); TRANSLITERATION_UKR_ALPHABET.put('Р', "R");
        TRANSLITERATION_UKR_ALPHABET.put('С', "S"); TRANSLITERATION_UKR_ALPHABET.put('Т', "T"); TRANSLITERATION_UKR_ALPHABET.put('У', "U");
        TRANSLITERATION_UKR_ALPHABET.put('Ф', "F"); TRANSLITERATION_UKR_ALPHABET.put('Х', "Kh"); TRANSLITERATION_UKR_ALPHABET.put('Ц', "Ts");
        TRANSLITERATION_UKR_ALPHABET.put('Ч', "Ch"); TRANSLITERATION_UKR_ALPHABET.put('Ш', "Sh"); TRANSLITERATION_UKR_ALPHABET.put('Щ', "Shch");
        TRANSLITERATION_UKR_ALPHABET.put('Ю', "Yu"); TRANSLITERATION_UKR_ALPHABET.put('Я', "Ya"); TRANSLITERATION_UKR_ALPHABET.put('Ь', "");
    }

    public String generateLogin(String fullName) {
        fullName = fullName.toUpperCase();
        String[] nameParts = fullName.split(" ");

        String name = nameParts[0];
        String lastName = nameParts[1];
        String patronymic = nameParts[2];

        String transliteratedName = transliterate(name);
        String transliteratedLastName = transliterate(lastName);
        String transliteratedPatronymic = transliterate(patronymic);

        String initials = transliteratedName.substring(0, 1) + transliteratedPatronymic.substring(0, 1);
        transliteratedLastName = transliteratedLastName.substring(0, 1) + transliteratedLastName.substring(1).toLowerCase();

        return transliteratedLastName + initials;
    }

    private String transliterate(String text) {
        if (text == null || text.isEmpty())
            return text;

        StringBuilder transliterated = new StringBuilder();

        for (char ch : text.toCharArray()) {
            String textChar = TRANSLITERATION_UKR_ALPHABET.getOrDefault(ch, String.valueOf(ch));
            transliterated.append(textChar);
        }

        return transliterated.toString();
    }
}
