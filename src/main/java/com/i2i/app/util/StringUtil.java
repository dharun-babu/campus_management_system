package com.i2i.app.util;
/**
 * This class that provides some common string related operation that are accessible for use across an all.
 */
public final class StringUtil {

    /**
     * <p>Checks whether the given string contains any special character instead of an alphabet.</p>
	 * 
     * @param str  Input string which is to be validated. 
     * @return validated string.
     * Ex:"ranjith" or "RaNiJtH", return true.
     * Ex:"r@njith!", return false.
     */
    public static boolean validateString(String str) {
        String duplicateStr = str.trim();
	    for (char character : duplicateStr.toCharArray()) {
	         if ((character == 32) || (character >= 65 && character <= 90) || (character >= 97 && character <= 122)) {
                 continue;
	         } else {
                 return false;
	         }
        }
        return true;
    }

	/**
     * <p>Checks whether the given character is either 'A' or 'B'.</p>
     *
     * @param character  The input character to be validated.
     * @return 'A' or 'B' if the character matches, otherwise returns '\u0000' (null character).
     */
    public static char validateChar(char character) {
        if (character == 'A' || character == 'B') {
            return character;
        }
        return '\u0000'; // '\u0000' represents null character in Unicode
    }
}