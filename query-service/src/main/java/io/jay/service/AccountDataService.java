package io.jay.service;

import io.jay.entity.AccountRepository;
import io.jay.entity.AccountView;
import io.jay.entity.value.FindAccountQuery;
import io.jay.entity.value.FindAllAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Service
public class AccountDataService {

    private final AccountRepository accountRepository;

    private final QueryGateway queryGateway;

    @Autowired
    public AccountDataService(AccountRepository accountRepository, QueryGateway queryGateway) {
        this.accountRepository = accountRepository;
        this.queryGateway = queryGateway;
    }

    public AccountView getAccount(String uuid){
        FindAccountQuery findAccountQuery = new FindAccountQuery(UUID.fromString(uuid));

        return queryGateway.query(findAccountQuery,
                ResponseTypes.instanceOf(AccountView.class)).join();
    }

    public Flux<AccountView> getAccountStream(String uuid){
        FindAccountQuery findAccountQuery = new FindAccountQuery(UUID.fromString(uuid));

        SubscriptionQueryResult<AccountView,AccountView> queryResult = queryGateway.subscriptionQuery(findAccountQuery,
                ResponseTypes.instanceOf(AccountView.class),
                ResponseTypes.instanceOf(AccountView.class));

         return Flux.concat(queryResult.initialResult(),queryResult.updates());
    }

    public List<AccountView> getAllAccounts() {
        FindAllAccountsQuery findAllAccountsQuery = new FindAllAccountsQuery();
        List<AccountView> accountViews = queryGateway.query(findAllAccountsQuery,
                ResponseTypes.multipleInstancesOf(AccountView.class)).join();
        return accountViews;
    }

    public Flux<AccountView> getAllAccountsFlux() {
        FindAllAccountsQuery findAllAccountsQuery = new FindAllAccountsQuery();
        SubscriptionQueryResult<List<AccountView>,AccountView> subscriptionQueryResult = queryGateway.subscriptionQuery(findAllAccountsQuery,
                ResponseTypes.multipleInstancesOf(AccountView.class),
                ResponseTypes.instanceOf(AccountView.class));
        Flux<AccountView> accountViewFlux =  Flux.fromIterable(subscriptionQueryResult.initialResult().block());
        Flux<AccountView> updateAccountViewFlux = subscriptionQueryResult.updates();
        return Flux.concat(accountViewFlux,updateAccountViewFlux);
    }
}
