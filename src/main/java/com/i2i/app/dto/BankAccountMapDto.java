package com.i2i.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountMapDto {
    private String bankName;
    private String branchName;
    private long accountNumber;
    private String ifscCode;
    private long mobileNumber;
}
