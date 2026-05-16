package com.banking;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;


    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @PostMapping
    public Account save(@Valid @RequestBody Account account){
        return accountService.saveAccount(account);
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));
    }

    @PutMapping("/{id}")
    public Account updateById(@PathVariable Long id,@RequestBody Account account){
        return accountService.updateById(id,account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        return accountService.deleteById(id);
    }

    @PostMapping("/transaction")
    public Transaction transfer(@RequestBody Transaction transaction){
        return transactionService.transferMoney(transaction);
    }
}
