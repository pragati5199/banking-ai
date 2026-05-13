package com.banking;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    AccountService accountService;

    @InjectMocks
    TransactionService transactionService;

    @Test
    public void TestHappyCaseTransfer(){

        Account sender = new Account();
        sender.setBalance(new BigDecimal("50000.00"));
        sender.setAccountNumber("ACC001");

        Account receiver = new Account();
        receiver.setBalance(new BigDecimal("20000.00"));
        receiver.setAccountNumber("ACC002");

        Transaction transfer = new Transaction();
        transfer.setTransAmount(new BigDecimal("5000.00"));
        transfer.setSenderAccountNumber(sender.getAccountNumber());
        transfer.setReceiverAccountNumber(receiver.getAccountNumber());
        transfer.setTransType(TransactionType.TRANSFER);

        when(accountService.getAccountByAccNumber("ACC001"))
                .thenReturn(Optional.of(sender));

        when(accountService.getAccountByAccNumber("ACC002"))
                .thenReturn(Optional.of(receiver));

        when(transactionRepository.save(any()))
                .thenReturn(transfer);

        Transaction result = transactionService.transferMoney(transfer);
        assertEquals(TransactionStatus.SUCCESS, result.getTransStatus());
    }

    @Test
    public void TestInsufficientFundsCaseTransfer(){

        Account sender = new Account();
        sender.setBalance(new BigDecimal("1000.00"));
        sender.setAccountNumber("ACC001");

        Account receiver = new Account();
        receiver.setBalance(new BigDecimal("20000.00"));
        receiver.setAccountNumber("ACC002");

        Transaction transfer = new Transaction();
        transfer.setTransAmount(new BigDecimal("5000.00"));
        transfer.setSenderAccountNumber(sender.getAccountNumber());
        transfer.setReceiverAccountNumber(receiver.getAccountNumber());
        transfer.setTransType(TransactionType.TRANSFER);

        when(accountService.getAccountByAccNumber("ACC001"))
                .thenReturn(Optional.of(sender));

//        when(accountService.getAccountByAccNumber("ACC002"))
//                .thenReturn(Optional.of(receiver));
//
//        when(transactionRepository.save(any()))
//                .thenReturn(transfer);

        assertThrows(ResponseStatusException.class, () -> {
            transactionService.transferMoney(transfer);
        });
    }

    @Test
    public void TestIvalidAmountCaseTransfer(){

        Account sender = new Account();
        sender.setBalance(new BigDecimal("1000.00"));
        sender.setAccountNumber("ACC001");

        Account receiver = new Account();
        receiver.setBalance(new BigDecimal("20000.00"));
        receiver.setAccountNumber("ACC002");

        Transaction transfer = new Transaction();
        transfer.setTransAmount(new BigDecimal("-1"));
        transfer.setSenderAccountNumber(sender.getAccountNumber());
        transfer.setReceiverAccountNumber(receiver.getAccountNumber());
        transfer.setTransType(TransactionType.TRANSFER);

//        when(accountService.getAccountByAccNumber("ACC001"))
//                .thenReturn(Optional.of(sender));

//        when(accountService.getAccountByAccNumber("ACC002"))
//                .thenReturn(Optional.of(receiver));
//
//        when(transactionRepository.save(any()))
//                .thenReturn(transfer);

        assertThrows(ResponseStatusException.class, () -> {
            transactionService.transferMoney(transfer);
        });
    }

}
