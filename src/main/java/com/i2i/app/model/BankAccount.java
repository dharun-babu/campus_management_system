package com.i2i.app.model;

import java.lang.StringBuilder;

public class BankAccount {
	private int accountId;
	private String bankName;
	private String branchName;
    private long accountNumber;
    private String ifscCode;
	private long mobileNumber;
    private Student student;

    public BankAccount() {}

    public BankAccount(String bankName, String branchName, long accountNumber, String ifscCode, long mobileNumber) {
		this.bankName = bankName;
		this.branchName = branchName;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
		this.mobileNumber = mobileNumber;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
    
	public int getAccountId() {
        return accountId;
    }

    public String getBankName() {
        return bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }
	
	public Student getStudent() {
        return student;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nBank Name : ").append(getBankName())
                     .append("\nBranch Name : ").append(getBranchName())
                     .append("\nAccount Number : ").append(getAccountNumber())
                     .append("\nIfsc Code : ").append(getIfscCode())
                     .append("\nMobile Number : ").append(getMobileNumber());
        return stringBuilder.toString();
    }
}