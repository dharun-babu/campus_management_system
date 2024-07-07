package com.i2i.app.service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.BankAccountResponseDto;
import com.i2i.app.dto.CreateBankAccountRequestDto;
import com.i2i.app.mapper.MapperInterface;
import com.i2i.app.model.BankAccount;
import com.i2i.app.repositories.BankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class handling operations related to bank account management.
 */
@Service
@Slf4j
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private MapperInterface mapperInterface;

    /**
     * <p>
     *     Saves a new bank account based on the provided request DTO.
     * </p>
     *
     * @param createBankAccountRequestDto The DTO containing bank account details to be saved.
     * @return The saved {@link BankAccount} details .
     * @throws StudentException if there is an error while saving the bank account.
     */
    public BankAccount saveBankAccount(CreateBankAccountRequestDto createBankAccountRequestDto) throws StudentException {
        try {
            log.debug("Saving bank account details of {}", createBankAccountRequestDto);
            BankAccount bankAccount = new BankAccount();
            bankAccount.setAccountNumber(createBankAccountRequestDto.getAccountNumber());
            bankAccount.setBankName(createBankAccountRequestDto.getBankName());
            bankAccount.setBranchName(createBankAccountRequestDto.getBranchName());
            bankAccount.setIfscCode(createBankAccountRequestDto.getIfscCode());
            bankAccount.setMobileNumber(createBankAccountRequestDto.getMobileNumber());
            BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
            log.debug("Successfully saved bank account details: {}", savedBankAccount);
            return savedBankAccount;
        } catch (Exception e) {
            throw new StudentException("Failed to save bank account", e);
        }
    }
}
