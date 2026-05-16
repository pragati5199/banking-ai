package com.banking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    public Transaction saveTransaction(Transaction transaction){

        return transactionRepository.save(transaction);
    }
    @Transactional
    public Transaction transferMoney(Transaction transaction){

        if ( transaction.getTransAmount().intValue() <= 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Transfer amount must be greater tha 0");
        } else {

            Account sender = accountRepository.findByAccountNumber(transaction.getSenderAccountNumber())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Sender Account not found"));

            Account receiver = accountRepository.findByAccountNumber(transaction.getReceiverAccountNumber())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Receiver Account not found"));
            if (sender.getBalance().compareTo(transaction.getTransAmount()) < 0){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Insufficient Funds in Account");
            }

            sender.setBalance(sender.getBalance().subtract(transaction.getTransAmount()));
            accountRepository.save(sender);

            receiver.setBalance(receiver.getBalance().add(transaction.getTransAmount()));
            accountRepository.save(receiver);

            transaction.setTransStatus(TransactionStatus.SUCCESS);
            transaction.setTransTimeStamp(LocalDateTime.now());

            return saveTransaction(transaction);
        }
    }

    public List<Transaction> getAllTransation() {

        return transactionRepository.findAll();
    }
}
