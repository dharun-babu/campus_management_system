package com.i2i.app.service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.BankAccountResponseDto;
import com.i2i.app.dto.CreateBankAccountRequestDto;

public interface BankAccountInterface {
    BankAccountResponseDto saveBankAccount(CreateBankAccountRequestDto createBankAccountRequestDto) throws StudentException;
}
