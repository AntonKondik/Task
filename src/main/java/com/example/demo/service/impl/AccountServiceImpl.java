package com.example.demo.service.impl;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.AccountService;
import com.example.demo.util.ContextHolder;
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
    public Account save(Account account){
        log.info("Пользователь " + contextHolder.getUserInfo() + " начал создание нового счета " + account.getAccountNumber());
        return accountRepository.save(account);
    }

    @Override
    public Account findByAccountNumber(String accountNumber){
        log.info("Пользователь " + contextHolder.getUserInfo() + " просматривает счет " + accountNumber);
        return accountRepository.findByAccountNumber(accountNumber);
    }
}
