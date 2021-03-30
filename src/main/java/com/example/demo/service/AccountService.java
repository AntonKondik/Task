package com.example.demo.service;

import com.example.demo.model.Account;

public interface AccountService {

    Account save(Account account);
    Account findByAccountNumber(String accountNumber);
}
