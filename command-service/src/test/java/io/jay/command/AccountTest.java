package io.jay.command;

import io.jay.common.command.CreateAccountCommand;
import io.jay.common.event.AccountCreatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class AccountTest {

    private FixtureConfiguration<Account> fixture;

    @Before
    public void setUp(){
        fixture = new AggregateTestFixture<>(Account.class);
    }

    @Test
    public void testFirstFixture(){
        UUID uid = UUID.randomUUID();
        fixture.given()
        .when(new CreateAccountCommand(uid,"Test"))
        .expectSuccessfulHandlerExecution()
        .expectEvents(new AccountCreatedEvent(uid,"Test"));
    }
}
