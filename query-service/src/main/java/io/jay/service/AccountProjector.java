package io.jay.service;

import io.jay.common.event.AccountCreatedEvent;
import io.jay.common.event.MoneyDepositedEvent;
import io.jay.common.event.MoneyWithdrawnEvent;
import io.jay.entity.AccountRepository;
import io.jay.entity.AccountView;
import io.jay.entity.value.FindAccountQuery;
import io.jay.entity.value.FindAllAccountsQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountProjector {

    private final AccountRepository repository;
    private final QueryUpdateEmitter queryUpdateEmitter;
    private final AccountDataService accountDataService;

    @Autowired
    public AccountProjector(AccountRepository repository, QueryUpdateEmitter queryUpdateEmitter, AccountDataService accountDataService) {
        this.repository = repository;
        this.queryUpdateEmitter = queryUpdateEmitter;
        this.accountDataService = accountDataService;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        AccountView accountView = new AccountView(event.getAccountId(),event.getName(),0.0);

        repository.save(accountView);

        queryUpdateEmitter.emit(FindAllAccountsQuery.class,
                query->true,
                accountView);

        queryUpdateEmitter.emit(FindAccountQuery.class,
                query -> query.getAccountId().equals(event.getAccountId()),
                accountView);
    }

    @EventHandler
    public void on(MoneyDepositedEvent event){
        UUID accountId = event.getAccountId();
        AccountView accountView = repository.findById(accountId).get();

        double newBalance =  accountView.getBalance() + event.getAmount();

        accountView.setBalance(newBalance);

        repository.save(accountView);

        queryUpdateEmitter.emit(FindAllAccountsQuery.class,
                query->true,
                accountView);

        queryUpdateEmitter.emit(FindAccountQuery.class,
                query -> query.getAccountId().equals(event.getAccountId()),
                accountView);
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event){
        UUID accountId = event.getAccountId();
        AccountView accountView = repository.findById(accountId).get();

        double newBalance =  accountView.getBalance() - event.getAmount();

        accountView.setBalance(newBalance);

        repository.save(accountView);
        queryUpdateEmitter.emit(FindAllAccountsQuery.class,
                query->true,
                accountView);

        queryUpdateEmitter.emit(FindAccountQuery.class,
                query -> query.getAccountId().equals(event.getAccountId()),
                accountView);
    }

    @QueryHandler
    public List<AccountView> handle(FindAllAccountsQuery query){
        System.out.println(query);
        return repository.findAll();
    }

    @QueryHandler
    public AccountView handle(FindAccountQuery query){
        System.out.println(query);
        query.getAccountId();
        return repository.findById(query.getAccountId()).orElseThrow(()->new IllegalArgumentException());
    }



}
