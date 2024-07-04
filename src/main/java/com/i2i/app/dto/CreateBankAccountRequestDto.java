package com.i2i.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.i2i.app.model.Student;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBankAccountRequestDto {
    private String bankName;
    private String branchName;
    private long accountNumber;
    private String ifscCode;
    private long mobileNumber;
    private StudentMapDto studentMapDto;

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

    public StudentMapDto getStudentMapDto() {
        return studentMapDto;
    }

    public void setStudentMapDto(StudentMapDto studentMapDto) {
        this.studentMapDto = studentMapDto;
    }
}
