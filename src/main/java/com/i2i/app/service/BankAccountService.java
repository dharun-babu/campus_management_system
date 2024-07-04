package com.i2i.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.BankAccountResponseDto;
import com.i2i.app.dto.CreateBankAccountRequestDto;
import com.i2i.app.mapper.MapperInterface;
import com.i2i.app.model.BankAccount;
import com.i2i.app.repositories.BankAccountRepository;

/**
 * This class for managing bank account-related operations.
 * This class provides methods to handle the business logic for creating and retrieving bank account details.
 */
@Service
@Slf4j
public class BankAccountService implements BankAccountInterface {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private MapperInterface mapperInterface;

    /**
     * <p>Creates a new bank account.
     * This method processes the request to create a new bank account, converts the request details,
     * and saves the new bank account.</p>
     *
     * @param createBankAccountRequestDto The request details for creating a new bank account.
     * @return The response details of the created bank account.
     * @throws StudentException if an error occurs while creating the bank account.
     */
    @Override
    public BankAccountResponseDto saveBankAccount(CreateBankAccountRequestDto createBankAccountRequestDto) throws StudentException {
        log.debug("Attempting to save bank account details");
        try {
            BankAccount bankAccount = bankAccountRepository.save(mapperInterface.convertToBankAccount(createBankAccountRequestDto));
            log.info("Successfully saved bank account details");
            return mapperInterface.convertToBankAccountResponseDto(bankAccount);
        } catch (Exception e) {
            log.error("Error occurred while saving bank account details: {}", e.getMessage(), e);
            throw new StudentException("Unable to save bank account details. Please try again later.", e);
        }
    }
}
