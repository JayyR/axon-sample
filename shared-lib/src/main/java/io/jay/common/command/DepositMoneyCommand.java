package io.jay.common.command;

import lombok.Data;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Value
public class DepositMoneyCommand {

    @TargetAggregateIdentifier
    private UUID accountId;

    private Double amount;
}
