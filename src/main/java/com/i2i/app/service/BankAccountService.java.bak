package com.i2i.app.service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dao.BankAccountDAO;
import com.i2i.app.model.BankAccount;

/**
 * <p> This class manages bank account-related operations.
 * It responsible to create bank accounts, check for existing account numbers, and handle resource cleanup.</p>
 */
public class BankAccountService {

    private BankAccountDAO bankAccountDAO = new BankAccountDAO();

    /**
     * <p> Creates a new BankAccount with the provided details.</p>
     *
     * @param bankName      The name of the bank where the account is held.
     * @param branchName    The name of the branch where the account is held.
     * @param accountNumber The unique account number for the bank account.
     * @param ifscCode      The IFSC code associated with the bank branch.
     * @param mobileNumber  The mobile number linked to the bank account.
     * @return A new BankAccount instance with the provided details.
     */
    public BankAccount getBankAccount(String bankName, String branchName, long accountNumber, String ifscCode, long mobileNumber) {
        return new BankAccount(bankName, branchName, accountNumber, ifscCode, mobileNumber);
    }

    /**
     * <p> Checks if a bank account with the given account number exists.
     * This method verifies the existence of an account number in the system.</p>
     *
     * @param accountNumber The account number to check for existence.
     * @return True if the account number exists, false otherwise.
     */
    public boolean isAccountNumberExists(long accountNumber) throws StudentException {
        return bankAccountDAO.existsByAccountNumber(accountNumber);
    }

    /**
     * <p> Closes the connection to release resources.
     * This method ensures that all resources are properly released.</p>
     */
    public void closeConnection() throws StudentException {
        bankAccountDAO.shutDown();
    }
}
