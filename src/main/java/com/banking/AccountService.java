package com.banking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Page<AccountResponseDTO> getAllAccounts(Pageable pageable){

            return accountRepository.findAll(pageable)
                    .map(AccountMapper::toResponseDTO);

    }

    public AccountResponseDTO saveAccount(AccountRequestDTO account){
        log.info("Creating new account with number: {}", account.getAccountNumber());
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
        log.warn("Deleting account with id: {}", id);
        accountRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public AccountResponseDTO updateById(Long id, AccountRequestDTO account) {

        Optional<Account> ac = accountRepository.findById(id);
        if (ac.isPresent()){
            ac.get().setAccountType(account.getAccountType());
            ac.get().setName(account.getName());
            log.info("Updating account with id: {}", id);
            return AccountMapper.toResponseDTO(accountRepository.save(ac.get()));

        }else {
            log.error("Account not found with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }
}
