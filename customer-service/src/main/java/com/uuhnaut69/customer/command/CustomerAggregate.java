package com.uuhnaut69.customer.command;

import com.uuhnaut69.customer.core.CreateCustomerCommand;
import com.uuhnaut69.customer.core.CreatedCustomerEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class CustomerAggregate {

  @AggregateIdentifier private String customerId;

  private String username;

  private BigDecimal credit;

  @CommandHandler
  public CustomerAggregate(CreateCustomerCommand createCustomerCommand) {
    apply(
        new CreatedCustomerEvent(
            createCustomerCommand.getCustomerId(),
            createCustomerCommand.getUsername(),
            BigDecimal.ZERO));
  }

  @EventSourcingHandler
  protected void on(CreatedCustomerEvent createdCustomerEvent) {
    this.customerId = createdCustomerEvent.getCustomerId();
    this.username = createdCustomerEvent.getUsername();
    this.credit = createdCustomerEvent.getCredit();
  }

  protected CustomerAggregate() {}
}
