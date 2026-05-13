package com.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public Transaction transfer(@RequestBody Transaction transaction){
        return transactionService.transferMoney(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransaction(){
        return transactionService.getAllTransation();
    }
}
