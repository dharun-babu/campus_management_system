package com.i2i.app.util;
/**
 * Class that provides some common string related operation that are accessible for use across an all.
 */
public final class StringUtil {
    /**
     * <p>Checks whether the given string contains any special character instead of an alphabet.</p>
     * @param str 
     *        Input string which is to be validated. 
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
   * <p>Checks whether the given character contains either 'A' or 'B' character </p>
   * @param character  
   *        Input character  which is to be validated. 
   * @return character .
   * Ex:'A' or 'B', retrns same character.
   * Ex:'c' or '-', returns 0.
   */
    public static char validateChar(char character) {
	    if (character == 65 || character == 66) {
	        return character;
	    }
	    return 0;
    }
}