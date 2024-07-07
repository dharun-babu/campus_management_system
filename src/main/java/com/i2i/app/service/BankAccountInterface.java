package com.i2i.app.service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.CreateBankAccountRequestDto;
import com.i2i.app.model.BankAccount;

public interface BankAccountInterface {
    BankAccount saveBankAccount(CreateBankAccountRequestDto createBankAccountRequestDto) throws StudentException;
}
