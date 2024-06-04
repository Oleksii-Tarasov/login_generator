package tek.developer.login_generator.service;

import java.util.HashMap;
import java.util.Map;

public class Generator {
    private static final Map<Character, String> TRANSLITERATION_UKR_ALPHABET = new HashMap<>();
    private static final Map<Character, String> TRANSLITERATION_UKR_ALPHABET_SPECIAL = new HashMap<>();

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

        TRANSLITERATION_UKR_ALPHABET_SPECIAL.put('Є', "ie");
        TRANSLITERATION_UKR_ALPHABET_SPECIAL.put('Ї', "i");
        TRANSLITERATION_UKR_ALPHABET_SPECIAL.put('Й', "i");
        TRANSLITERATION_UKR_ALPHABET_SPECIAL.put('Ю', "iu");
        TRANSLITERATION_UKR_ALPHABET_SPECIAL.put('Я', "ia");
    }

    public String generateLogin(String fullName) {
        fullName = fullName.toUpperCase();
        String[] nameParts = fullName.split(" ");

        if (nameParts.length != 3) {
            throw new IllegalArgumentException("The full name must consist of three parts in order: first name, last name, patronymic.");
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

    private String transliterateText(String text) {
        StringBuilder transliteratedText = new StringBuilder();

        char firstLetter = text.charAt(0);
        String lastLetters = text.substring(1);

        transliteratedText.append(TRANSLITERATION_UKR_ALPHABET.getOrDefault(firstLetter, String.valueOf(firstLetter)));

        for (char ch : lastLetters.toCharArray()) {
            if (TRANSLITERATION_UKR_ALPHABET_SPECIAL.containsKey(ch))
                transliteratedText.append(TRANSLITERATION_UKR_ALPHABET_SPECIAL.getOrDefault(ch, String.valueOf(ch)));
            else
                transliteratedText.append(TRANSLITERATION_UKR_ALPHABET.getOrDefault(ch, String.valueOf(ch)));
        }

        return transliteratedText.toString();
    }
}
