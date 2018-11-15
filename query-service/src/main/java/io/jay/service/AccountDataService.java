package io.jay.service;

import io.jay.entity.AccountRepository;
import io.jay.entity.AccountView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountDataService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountDataService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    public List<AccountView> getAllAccounts() {
        return accountRepository.findAll();
    }
}
