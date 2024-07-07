package com.i2i.app.util;

import com.i2i.app.customexception.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * This class is for date-related operations.
 */
public final class DateUtil {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

    static {
        dateFormat.setLenient(false); // Ensure strict parsing for the date format
        datetimeFormat.setLenient(false); // Ensure strict parsing for the datetime format
    }

    /**
     * <p>
     *     Checks if the given date string is in a valid format and parses it.
     * </p>
     *
     * @param date  The date string to be checked and parsed.
     * @return true if the date string is valid, false otherwise.
     * @throws ValidationException If unable to parse the date string due to format mismatch or invalid day/month.
     */
    public static boolean isFormatDate(String date) throws ValidationException {
        try {
            Date parsedDate = dateFormat.parse(date);
            return (isValidDate(parsedDate) && isDateValid(parsedDate));
        } catch (ParseException e) {
            try {
                Date parsedDatetime = datetimeFormat.parse(date);
                return true; // Accepts datetime format
            } catch (ParseException ex) {
                throw new ValidationException("The given date format is invalid.");
            }
        }
    }

    /**
     * <p>
     *     Validates if the given date is a valid date with correct day and month.
     * </p>
     *
     * @param date The date to be validated.
     * @return true if the date is valid, false otherwise.
     * @throws ValidationException If the date has an invalid day or month.
     */
    private static boolean isValidDate(Date date) throws ValidationException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month < 1 || month > 12) {
            throw new ValidationException("Invalid month in the date.");
        }
        if (day < 1 || day > getDaysInMonth(year, month)) {
            throw new ValidationException("Invalid day in the date.");
        }
        return true;
    }

    /**
     * <p>
     *     Returns the number of days in a given month of a given year.
     * </p>
     *
     * @param year  The year.
     * @param month The month.
     * @return The number of days in the month.
     */
    private static int getDaysInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // Setting month to zero-based for calendar
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * <p>
     *     Calculates the difference in years between the given date and the current date.
     * </p>
     *
     * @param date The date to calculate the difference from.
     * @return The number of years between the given date and the current date.
     */
    public static int calculateYearDifference(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar inputCalendar = Calendar.getInstance();
        inputCalendar.setTime(date);
        int currentYear = currentCalendar.get(Calendar.YEAR);
        int inputYear = inputCalendar.get(Calendar.YEAR);
        int yearDifference = currentYear - inputYear;
        if (currentCalendar.get(Calendar.DAY_OF_YEAR) < inputCalendar.get(Calendar.DAY_OF_YEAR)) {
            yearDifference--;
        }
        return yearDifference;
    }

    /**
     * <p>
     *     Checks if the given date is not in the future and is within the last 18 years.
     * </p>
     *
     * @param date The date to be checked.
     * @return true if the date is not in the future and is within the last 18 years, false otherwise.
     * @throws ValidationException If the date is in the future or older than 18 years.
     */
    public static boolean isDateValid(Date date) throws ValidationException {
        Date currentDate = new Date();
        if (date.after(currentDate)) {
            throw new ValidationException("The given date is in the future.");
        }
        int yearDifference = calculateYearDifference(date);
        if (yearDifference < 3 || yearDifference > 18) {
            throw new ValidationException("The given date is older than 18 years or too recent.");
        }
        return true;
    }
}
