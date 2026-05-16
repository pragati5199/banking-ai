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

    public List<AccountResponseDTO> getAllAccounts(){

            return accountRepository.findAll()
                    .stream()
                    .map(AccountMapper::toResponseDTO)
                    .toList();

    }

    public AccountResponseDTO saveAccount(AccountRequestDTO account){
        return AccountMapper.toResponseDTO(accountRepository.save(AccountMapper.toEntity(account)));
    }

    public Optional<AccountResponseDTO> getAccountById(Long id){
        return accountRepository.findById(id).map((AccountMapper::toResponseDTO));
    }

    public Optional<AccountResponseDTO> getAccountByAccNumber(String accNumber){
        return accountRepository.findByAccountNumber(accNumber).map((AccountMapper::toResponseDTO));
    }

    public ResponseEntity<Void> deleteById(Long id){

        getAccountById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));

        accountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public AccountResponseDTO updateById(Long id, AccountRequestDTO account) {

        Optional<Account> ac = accountRepository.findById(id);
        if (ac.isPresent()){
            ac.get().setAccountType(account.getAccountType());
            ac.get().setName(account.getName());
            return AccountMapper.toResponseDTO(accountRepository.save(ac.get()));

        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}
