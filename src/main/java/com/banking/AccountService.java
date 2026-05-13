package com.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    public Account saveAccount(Account account){
        return accountRepository.save(account);
    }

    public Optional<Account> getAccountById(Long id){
        return accountRepository.findById(id);
    }

    public Optional<Account> getAccountByAccNumber(String accNumber){
        return accountRepository.findByAccountNumber(accNumber);
    }

    public ResponseEntity<Void> deleteById(Long id){

        getAccountById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));

        accountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public Account updateById(Long id, Account account) {

        Optional<Account> ac = getAccountById(id);
        if (!ac.isEmpty()){
            ac.get().setAccountType(account.getAccountType());
            ac.get().setName(account.getName());

            saveAccount(ac.get());
            return ac.get();

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}
