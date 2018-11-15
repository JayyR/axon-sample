package io.jay.command;


import io.jay.common.command.CreateAccountCommand;
import io.jay.common.command.DepositMoneyCommand;
import io.jay.common.command.WithdrawMoneyCommand;
import io.jay.common.event.AccountCreatedEvent;
import io.jay.common.event.MoneyDepositedEvent;
import io.jay.common.event.MoneyWithdrawnEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class Account {

    @AggregateIdentifier
    private UUID accountId;

    private Double balance;

    @CommandHandler
    public Account(CreateAccountCommand command){
        apply(new AccountCreatedEvent(command.getAccountId(),command.getName()));
    }


    @CommandHandler
    public void handle(DepositMoneyCommand command){
        apply(new MoneyDepositedEvent(command.getAccountId(),command.getAmount()));
    }

    @CommandHandler
    public void handle(WithdrawMoneyCommand command){
        apply(new MoneyWithdrawnEvent(command.getAccountId(),command.getAmount()));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent event){
        this.accountId = event.getAccountId();
        this.balance = 0.0;
    }

    @EventSourcingHandler
    protected void on(MoneyDepositedEvent event){
        this.balance = balance + event.getAmount();
    }

    @EventSourcingHandler
    protected void on(MoneyWithdrawnEvent event){
        this.balance  = balance -  event.getAmount();
    }
}
