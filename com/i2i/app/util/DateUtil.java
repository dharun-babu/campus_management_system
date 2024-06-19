package com.i2i.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.i2i.app.customexception.StudentException;

public final class DateUtil {
    private static SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
  /**
   * <p>Checks given date in format(yyyy-mm-dd) or not</p>
   *
   * @param date 
   *         holds the date in string in format of (yyyy-mm-dd).
   * @return Date 
   *         If the date format is correct, parsed date with the given format.
   *         If the format is not correct, returns null.
   * @throws ParseException 
   *         This exception is raised when unable to parse another date format(yyyy/mm/dd)
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
   * Calculate the difference between the given date year and current date year.
   *
   * @param date 
   *        The date in the format ("yyyy-mm-dd") and must be lesser than current date .
   * @return int 
   *        The count of number of years between the given date year and the current date year.
   */
    public static int calculateYearDifference(Date date) {
         Date currentDate = new Date();
         return currentDate.getYear() - date.getYear();
    }
}
