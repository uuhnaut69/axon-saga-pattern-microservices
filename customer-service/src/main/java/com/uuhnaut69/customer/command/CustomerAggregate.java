package com.uuhnaut69.customer.command;

import com.uuhnaut69.customer.core.AddCreditCommand;
import com.uuhnaut69.customer.core.AddedCreditEvent;
import com.uuhnaut69.customer.core.CreateCustomerCommand;
import com.uuhnaut69.customer.core.CreatedCustomerEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
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

  @CommandHandler
  public void handle(AddCreditCommand addCreditCommand) {
    if (addCreditCommand.getCredit().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Credit must be greater than 0");
    }
    apply(new AddCreditCommand(addCreditCommand.getCustomerId(), addCreditCommand.getCredit()));
  }

  @EventSourcingHandler
  protected void on(CreatedCustomerEvent createdCustomerEvent) {
    this.customerId = createdCustomerEvent.getCustomerId();
    this.username = createdCustomerEvent.getUsername();
    this.credit = createdCustomerEvent.getCredit();
  }

  @EventSourcingHandler
  public void on(AddedCreditEvent addedCreditEvent) {
    this.customerId = addedCreditEvent.getCustomerId();
    this.credit = addedCreditEvent.getCredit();
  }
}
