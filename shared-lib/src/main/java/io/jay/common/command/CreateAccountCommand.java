package io.jay.common.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountCommand {

    @TargetAggregateIdentifier
    private UUID accountId;

    private String name;


}
