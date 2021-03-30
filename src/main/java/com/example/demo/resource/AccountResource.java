package com.example.demo.resource;
import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.BalanceService;
import com.example.demo.util.ContextHolder;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountResource {

    private final AccountService accountService;
    private final BalanceService balanceService;
    private final ContextHolder contextHolder;

    @RequestMapping(method = POST, value = "/accounts/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> add(Account account, @RequestHeader String userInfo) {

        contextHolder.setUserInfo(userInfo);
        try {
            Account res = accountService.save(account);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Ошибка: " + ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = GET, value = "/accounts/get/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> findByAccountNumber(@PathVariable("accountNumber") String accountNumber,
                                                       @RequestHeader String userInfo) {

        contextHolder.setUserInfo(userInfo);
        try {
            Account res = accountService.findByAccountNumber(accountNumber);
            if (res != null) {
                return new ResponseEntity<>(res, HttpStatus.OK);
            } else {
                log.error("Аккаунт не найден");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = PUT, value = "/accounts/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> changeBalance(@RequestParam("id") Long id, @RequestParam("diff") Double diff,
                                                 @RequestHeader String userInfo) {

        log.info("Пользователь " + userInfo + " запросил изменение баланса счета id = " + id);
        contextHolder.setUserInfo(userInfo);
        try {
            Account res = balanceService.change(id, diff);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (NotFoundException ex) {
            log.error("Ошибка: " + ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            log.error("Ошибка: " + ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
