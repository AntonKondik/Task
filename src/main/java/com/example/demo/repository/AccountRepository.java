package com.example.demo.repository;
import com.example.demo.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByAccountNumber(String accountNumber);

//    @Query(value = "select a from Account a where a.accountNumber = :accountNumber")
//    Optional<Long> findAccountByAccountNumber(@Param("accountNumber")String accountNumber);
}
