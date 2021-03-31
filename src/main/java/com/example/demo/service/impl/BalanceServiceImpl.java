package com.example.demo.service.impl;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.BalanceService;
import com.example.demo.util.ContextHolder;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BalanceServiceImpl implements BalanceService {

    private final AccountRepository accountRepository;
    private final ContextHolder contextHolder;

    @Override
    public Account change(Long id, Double diff) throws NotFoundException {
        log.info("Пользователь " + contextHolder.getUserInfo() + " изменяет баланс счета id = " + id + " на сумму = " + diff);
        Optional<Account> oAccount = accountRepository.findById(id);
        if (!oAccount.isPresent())
            throw new NotFoundException("Аккаунт не найден");
        Account account = oAccount.get();
        account.setAmount(account.getAmount() + diff);//Здесь так и не понял в чем проблема - локально дернул метод - в базе значение поменялось
        return accountRepository.save(account);
    }
}
