package io.jay.resource;

import io.jay.common.command.CreateAccountCommand;
import io.jay.common.command.DepositMoneyCommand;
import io.jay.common.command.WithdrawMoneyCommand;
import io.jay.common.event.MoneyWithdrawnEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class AccountResource {

    private static final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final CommandGateway commandGateway;


    @Autowired
    public AccountResource(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/accounts")
    public CompletableFuture<CreateAccountCommand> createAccount(@RequestBody AccountDto accountDto){
        UUID accountId = UUID.randomUUID();

        CreateAccountCommand createAccountCommand = new CreateAccountCommand(accountId,accountDto.getName());

        return commandGateway.send(createAccountCommand);
    }



    @PutMapping("/accounts/{accountId}/deposit/{amount}")
    public CompletableFuture<DepositMoneyCommand> depositMoney(@PathVariable UUID accountId, @PathVariable double amount){

        return commandGateway.send(new DepositMoneyCommand(accountId,amount));
    }


    @PutMapping("/accounts/{accountId}/withdraw/{amount}")
    public CompletableFuture<DepositMoneyCommand> withdrawMoney(@PathVariable UUID accountId, @PathVariable double amount){

        return commandGateway.send(new WithdrawMoneyCommand(accountId,amount));
    }

}
