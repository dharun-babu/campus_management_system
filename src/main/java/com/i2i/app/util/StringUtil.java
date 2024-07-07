package com.i2i.app.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class for common string operations.
 */
public final class StringUtil {

    public enum Subject {
        TAMIL, ENGLISH, MATHS, SCIENCE, SOCIAL_SCIENCE, COMPUTER_SCIENCE, BIOLOGY, COMMERCE;
    }

    private static final Set<String> VALID_SUBJECTS = new HashSet<>(Arrays.asList(
            "TAMIL", "ENGLISH", "MATHS", "SCIENCE", "SOCIAL_SCIENCE", "COMPUTER_SCIENCE", "BIOLOGY", "COMMERCE"
    ));

    /**
     * <p>
     *     Validates if a string contains only alphabets.
     * </p>
     *
     * @param str The string to validate.
     * @return true if the string contains only alphabets, false otherwise.
     *        Sample Input: "ranjith" or "RaNjItH MaStEr", return true
     * Use: {@link StringUtil#validateString(String)}
     */
    public static boolean validateString(String str) {
        String trimmedStr = str.trim();
        for (char character : trimmedStr.toCharArray()) {
            if ((character == 32) || (character >= 65 && character <= 90) || (character >= 97 && character <= 122)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     *     Validates if a character is 'A', 'B', 'C', or 'D'.
     * </p>
     *
     * @param character The character to validate.
     * @return 'A', 'B', 'C', or 'D' if the character matches, otherwise '\u0000'.
     * Use: {@link StringUtil#validateChar(char)}
     */
    public static char validateChar(char character) {
        if (character == 'A' || character == 'B' || character == 'C' || character == 'D') {
            return character;
        }
        return '\u0000';
    }

    /**
     * <p>
     *     Validates if a string is a valid subject.
     * </p>
     *
     * @param subject The subject to validate.
     * @return true if the subject is valid, false otherwise.
     * Use: {@link StringUtil#isValidSubject(String)}
     */
    public static boolean isValidSubject(String subject) {
        return VALID_SUBJECTS.contains(subject.toUpperCase());
    }
}
