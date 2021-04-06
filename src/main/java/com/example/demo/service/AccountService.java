package com.example.demo.service;

import com.example.demo.dto.AccountDto;
import javassist.NotFoundException;

public interface AccountService {

    AccountDto save(AccountDto accountDto);
    AccountDto findByAccountNumber(String accountNumber) throws NotFoundException;
}
