package com.i2i.app.util;

import java.util.regex.Pattern;

/**
 * This class for validating common input patterns.
 */
public class ValidationUtil {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^[789]\\d{9}$");
    private static final Pattern ACCOUNT_PATTERN = Pattern.compile("^\\d{16}$");
    private static final Pattern IFSC_PATTERN = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    /**
     * <p>
     *     Validates a phone number.
     * </p>
     *
     * @param phoneNumber The phone number to validate.
     * @return true if the phone number is valid, false otherwise.
     *      Sample Input: 9876543210, size is 10 so return true
     * Use: {@link ValidationUtil#isValidPhoneNumber(long)}
     */
    public static boolean isValidPhoneNumber(long phoneNumber) {
        String phoneNumberStr = String.valueOf(phoneNumber);
        return PHONE_PATTERN.matcher(phoneNumberStr).matches();
    }

    /**
     * <p>
     *     Validates an account number.
     * </p>
     *
     * @param accountNumber The account number to validate.
     * @return true if the account number is valid, false otherwise.
     *      Sample Input: 1234567890123456 size is equal to 16 return true
     * Use: {@link ValidationUtil#isValidAccountNumber(long)}
     */
    public static boolean isValidAccountNumber(long accountNumber) {
        String accountNumberStr = String.valueOf(accountNumber);
        return ACCOUNT_PATTERN.matcher(accountNumberStr).matches();
    }

    /**
     * <p>
     *     Validates an IFSC code.
     * </p>
     *
     * @param ifscCode The IFSC code to validate.
     * @return true if the IFSC code is valid, false otherwise.
     * Use: {@link ValidationUtil#isValidIfscCode(String)}
     */
    public static boolean isValidIfscCode(String ifscCode) {
        return ifscCode != null && IFSC_PATTERN.matcher(ifscCode).matches();
    }

    /**
     * <p>
     *     Validates a standard (1 to 12).
     * </p>
     *
     * @param standard The standard to validate.
     * @return true if the standard is valid, false otherwise.
     * Use: {@link ValidationUtil#isValidStandard(int)}
     */
    public static boolean isValidStandard(int standard) {
        return standard >= 1 && standard <= 12;
    }
}
