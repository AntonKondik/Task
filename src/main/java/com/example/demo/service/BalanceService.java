package com.example.demo.service;

import com.example.demo.dto.AccountDto;
import javassist.NotFoundException;

public interface BalanceService {

    AccountDto change(Long id, Double diff) throws NotFoundException;
}
