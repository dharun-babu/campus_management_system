package com.i2i.app.service;

import java.util.ArrayList;
import java.util.List;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dao.BankAccountDAO;
import com.i2i.app.model.BankAccount;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;

/**
 * This class for managing bank accounts.
 * This class provides methods to handle bank account creation, checking the existence of an account number, and closing connections.
 */
public class BankAccountService {
	private BankAccount bankAccount = new BankAccount();
	private BankAccountDAO bankAccountDAO = new BankAccountDAO();

	/**
	 * <p>Creates a new BankAccount with the provided details.</p>
	 *
	 * @param bankName      the name of the bank
	 * @param branchName    the name of the branch
	 * @param accountNumber the account number
	 * @param ifscCode      the IFSC code of the branch
	 * @param mobileNumber  the mobile number associated with the account
	 * @return a new BankAccount 
	 */
	public BankAccount getBankAccount(String bankName, String branchName, long accountNumber, String ifscCode, long mobileNumber) {
		return new BankAccount(bankName, branchName, accountNumber, ifscCode, mobileNumber);
	}

	/**
	 * <p> Checks if a bank account with the given account number exists.</p>
	 *
	 * @param accountNumber the account number to check
	 * @return true if the account number exists, false otherwis
	 */
	public boolean isAccountNumberExists(long accountNumber) throws StudentException {
		return bankAccountDAO.existsByAccountNumber(accountNumber);
	}

	/**
	 * <p>Closes the connection to the data source.</p>
	 */
	public void closeConnection() throws StudentException {
		bankAccountDAO.shutDown();
	}
}
