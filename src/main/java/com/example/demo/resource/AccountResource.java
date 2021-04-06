package com.example.demo.resource;
import com.example.demo.dto.AccountDto;
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
@RequestMapping(value = "/accounts")
@RequiredArgsConstructor
public class AccountResource {

    private final AccountService accountService;
    private final BalanceService balanceService;
    private final ContextHolder contextHolder;

    @RequestMapping(method = POST, value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> add(@RequestBody AccountDto accountDto, @RequestHeader("userInfo") String userInfo) {
        contextHolder.setUserInfo(userInfo);
        AccountDto res = accountService.save(accountDto);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(method = GET, value = "/get/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> findByAccountNumber(@PathVariable("accountNumber") String accountNumber,
                                      @RequestHeader("userInfo") String userInfo) throws NotFoundException {
        contextHolder.setUserInfo(userInfo);
        AccountDto res = accountService.findByAccountNumber(accountNumber);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @RequestMapping(method = PUT, value = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDto> changeBalance(@RequestParam("id") Long id, @RequestParam("diff") Double diff,
                                      @RequestHeader("userInfo") String userInfo) throws NotFoundException {
        log.info("Пользователь " + userInfo + " запросил изменение баланса счета id = " + id);
        contextHolder.setUserInfo(userInfo);
        AccountDto res = balanceService.change(id, diff);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
