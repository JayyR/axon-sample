package io.jay.service;

import io.jay.common.event.AccountCreatedEvent;
import io.jay.common.event.MoneyDepositedEvent;
import io.jay.common.event.MoneyWithdrawnEvent;
import io.jay.entity.AccountRepository;
import io.jay.entity.AccountView;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountProjector {

    private final AccountRepository repository;

    @Autowired
    public AccountProjector(AccountRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        AccountView accountView = new AccountView(event.getAccountId(),event.getName(),0.0);

        repository.save(accountView);
    }

    @EventHandler
    public void on(MoneyDepositedEvent event){
        UUID accountId = event.getAccountId();
        AccountView accountView = repository.findById(accountId).get();

        double newBalance =  accountView.getBalance() + event.getAmount();

        accountView.setBalance(newBalance);

        repository.save(accountView);
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event){
        UUID accountId = event.getAccountId();
        AccountView accountView = repository.findById(accountId).get();

        double newBalance =  accountView.getBalance() - event.getAmount();

        accountView.setBalance(newBalance);

        repository.save(accountView);
    }


}
