package com.i2i.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.i2i.app.customexception.StudentException;
/**
 * This class for date-related operations.
 */
public final class DateUtil {
    private static SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
	/**
     * <p> Checks if the given date string is in the format "yyyy-mm-dd" and parses it.</p>
     *
     * @param date  The date string to be checked and parsed.
     * @return Date Parsed from the string if format is correct, null otherwise.
     * @throws StudentException If unable to parse the date string due to format mismatch.
     */
    public static Date checkAndFormatDate(String date) throws StudentException {
        try {
            Date parsedDate = sqlDateFormat.parse(date);
            return parsedDate;
        } catch (ParseException e) {
            throw new StudentException("Unable to parse the given date format.", e);
        }
    }

    /**
     * <p> Calculates the difference in years between the given date and the current date.</p>
     *
     * @param date  The date to calculate the difference from.
     * @return The number of years between the given date and the current date.
     */
    public static int calculateYearDifference(Date date) {
         Date currentDate = new Date();
         return currentDate.getYear() - date.getYear();
    }
}
