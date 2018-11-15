package io.jay.controller;

import io.jay.entity.AccountView;
import io.jay.service.AccountDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountViewController {

    @Autowired
    private  AccountDataService accountDataService;


    @GetMapping("/accounts")
    public List<AccountView> getAllAccounts() {
        return accountDataService.getAllAccounts();
    }
    
}
