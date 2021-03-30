package com.example.demo.service;

import com.example.demo.model.Account;
import javassist.NotFoundException;

public interface BalanceService {

    Account change(Long id, Double diff) throws NotFoundException;
}
