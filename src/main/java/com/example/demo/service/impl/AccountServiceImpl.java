package com.example.demo.service.impl;

import com.example.demo.converter.AccountConverter;
import com.example.demo.dto.AccountDto;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;
import com.example.demo.util.ContextHolder;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ContextHolder contextHolder;

    @Override
    public AccountDto save(AccountDto accountDto){
        log.info("Пользователь " + contextHolder.getUserInfo() + " начал создание нового счета " + accountDto.getAccountNumber());
        Account res = accountRepository.save(AccountConverter.convertToAccount(accountDto));
        return AccountConverter.convertToAccountDto(res);
    }

    @Override
    public AccountDto findByAccountNumber(String accountNumber) throws NotFoundException {
        log.info("Пользователь " + contextHolder.getUserInfo() + " просматривает счет " + accountNumber);
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null)
            throw new NotFoundException("Аккаунт с номером " + accountNumber + " не найден");
        return AccountConverter.convertToAccountDto(account);
    }
}
