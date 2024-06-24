package com.i2i.app.model;

import javax.persistence.*;

@Entity
@Table(name = "bankaccount")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "account_number", unique = true, length = 16)
    private long accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "mobile_number", length = 10)
    private long mobileNumber;

    @OneToOne(mappedBy = "bankAccount", cascade = CascadeType.ALL)
    private Student student;


    public BankAccount() {
    }

    public BankAccount(String bankName, String branchName, long accountNumber, String ifscCode, long mobileNumber) {
        this.bankName = bankName;
        this.branchName = branchName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.mobileNumber = mobileNumber;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\nBank Name : ").append(bankName)
                .append("\nBranch Name : ").append(branchName)
                .append("\nAccount Number : ").append(accountNumber)
                .append("\nIfsc Code : ").append(ifscCode)
                .append("\nMobile Number : ").append(mobileNumber)
                .toString();
    }
}
