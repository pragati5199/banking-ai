package com.banking;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;


    @GetMapping
    public Page<AccountResponseDTO>getAllAccounts(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size,
                                                  @RequestParam(defaultValue = "id") String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return accountService.getAllAccounts(pageable);
    }

    @PostMapping
    public AccountResponseDTO save(@Valid @RequestBody AccountRequestDTO account){
        return accountService.saveAccount(account);
    }

    @GetMapping("/{id}")
    public AccountResponseDTO getAccountById(@PathVariable Long id){
        return accountService.getAccountById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));
    }

    @PutMapping("/{id}")
    public AccountResponseDTO updateById(@PathVariable Long id,@RequestBody AccountRequestDTO account){
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
