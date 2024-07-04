package com.i2i.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.i2i.app.customexception.StudentException;

/**
 * This class for date-related operations.
 */
public final class DateUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    /**
     * Checks if the given date string is in the format "yyyy-MM-dd" or "EEE MMM dd HH:mm:ss zzz yyyy" and parses it.
     *
     * @param date  The date string to be checked and parsed.
     * @return true if the date string is valid, false otherwise.
     * @throws StudentException If unable to parse the date string due to format mismatch.
     */
    public static boolean checkAndFormatDate(String date) throws StudentException {
        try {
            // Try parsing with date format
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            // If parsing with date format fails, try datetime format
            try {
                datetimeFormat.parse(date);
                return true;
            } catch (ParseException ex) {
                throw new StudentException("Unable to parse the given date format.", ex);
            }
        }
    }

    /**
     * Calculates the difference in years between the given date and the current date.
     *
     * @param date  The date to calculate the difference from.
     * @return The number of years between the given date and the current date.
     */
    public static int calculateYearDifference(Date date) {
        Date currentDate = new Date();
        return currentDate.getYear() - date.getYear();
    }
}
