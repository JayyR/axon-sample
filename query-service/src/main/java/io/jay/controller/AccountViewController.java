package io.jay.controller;

import io.jay.entity.AccountView;
import io.jay.entity.value.FindAllAccountsQuery;
import io.jay.service.AccountDataService;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SimpleQueryBus;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RestController
public class AccountViewController {

    private  final AccountDataService accountDataService;

    @Autowired
    public AccountViewController(AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    @GetMapping("/accounts")
    public List<AccountView> getAllAccounts() {

        return accountDataService.getAllAccounts();

    }


    @GetMapping(value = "/accounts/stream",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<AccountView> streamAllAccounts() {

        return accountDataService.getAllAccountsFlux();

    }

    @GetMapping(value = "/accounts/{accountId}")
    public AccountView getAccount(@PathVariable("accountId")String accountId) {
        return accountDataService.getAccount(accountId);

    }

    @GetMapping(value = "/accounts/{accountId}/stream",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<AccountView> getAccountStream(@PathVariable("accountId")String accountId) {
        return accountDataService.getAccountStream(accountId);

    }
}
