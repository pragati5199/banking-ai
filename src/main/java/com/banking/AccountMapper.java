package com.banking;

public class AccountMapper {

    public static Account toEntity(AccountRequestDTO dto){

        Account account = new Account();

        account.setAccountNumber(dto.getAccountNumber());
        account.setBalance(dto.getBalance());
        account.setAccountType(dto.getAccountType());
        account.setName(dto.getName());
        return account;
    }

    public static AccountResponseDTO toResponseDTO(Account account){

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();

        accountResponseDTO.setName(account.getName());
        accountResponseDTO.setAccountNumber(account.getAccountNumber());
        accountResponseDTO.setAccountType(account.getAccountType());
        accountResponseDTO.setId(account.getId());
        accountResponseDTO.setBalance(account.getBalance());

        return  accountResponseDTO;
    }
}
