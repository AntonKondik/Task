package com.example.demo.converter;

import com.example.demo.dto.AccountDto;
import com.example.demo.model.Account;
import lombok.NonNull;

public class AccountConverter {

    public static Account convertToAccount(@NonNull AccountDto accountDto){
        return Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .bic(accountDto.getBic())
                .amount(accountDto.getAmount())
                .build();
    }

    public static AccountDto convertToAccountDto(@NonNull Account account){
        return AccountDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .bic(account.getBic())
                .amount(account.getAmount())
                .build();
    }
}
