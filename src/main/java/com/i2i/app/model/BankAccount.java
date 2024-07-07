package com.i2i.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "bankaccount")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID accountId;

    @Column(name = "bank_name", length = 100, nullable = false)
    private String bankName;

    @Column(name = "branch_name", length = 30, nullable = false)
    private String branchName;

    @Column(name = "account_number", length = 16)
    private long accountNumber;

    @Column(name = "ifsc_code", nullable = false)
    private String ifscCode;

    @Column(name = "mobile_number", length = 10, nullable = false)
    private long mobileNumber;

    @JsonIgnore
    @OneToOne(mappedBy = "bankAccount",cascade = CascadeType.ALL)
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

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
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
        return "BankAccount{" +
                "accountId=" + accountId +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", accountNumber=" + accountNumber +
                ", ifscCode='" + ifscCode + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", student=" + student +
                '}';
    }
}
