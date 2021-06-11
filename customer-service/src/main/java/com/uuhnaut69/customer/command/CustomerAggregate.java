package com.uuhnaut69.customer.command;

import com.uuhnaut69.common.ordersaga.*;
import com.uuhnaut69.customer.core.CreateCustomerCommand;
import com.uuhnaut69.customer.core.CreatedCustomerEvent;
import com.uuhnaut69.customer.core.DepositCommand;
import com.uuhnaut69.customer.core.DepositedEvent;
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

  private BigDecimal balance;

  @CommandHandler
  public CustomerAggregate(CreateCustomerCommand command) {
    apply(
        new CreatedCustomerEvent(command.getCustomerId(), command.getUsername(), BigDecimal.ZERO));
  }

  @CommandHandler
  public void handle(DepositCommand command) {
    if (command.getBalance().compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Credit must be greater than 0");
    }
    apply(new DepositedEvent(command.getCustomerId(), command.getBalance()));
  }

  @CommandHandler
  public void handle(ReserveCustomerBalanceCommand event) {
    if (balance.compareTo(event.getTotalPrice()) < 0) {
      apply(
          new ReservedCustomerBalanceFailedEvent(
              customerId, event.getOrderId(), event.getTotalPrice()));
    } else {
      apply(
          new ReservedCustomerBalanceSuccessfullyEvent(
              customerId, event.getOrderId(), event.getTotalPrice()));
    }
  }

  @CommandHandler
  public void handle(CompensateCustomerBalanceCommand command) {
    apply(new CompensatedCustomerBalanceEvent(command.getCustomerId(), command.getAmount()));
  }

  @EventSourcingHandler
  public void on(CreatedCustomerEvent event) {
    this.customerId = event.getCustomerId();
    this.username = event.getUsername();
    this.balance = event.getBalance();
  }

  @EventSourcingHandler
  public void on(DepositedEvent event) {
    this.balance = event.getBalance();
  }

  @EventSourcingHandler
  public void on(ReservedCustomerBalanceSuccessfullyEvent event) {
    this.balance = this.balance.subtract(event.getTotalPrice());
  }

  @EventSourcingHandler
  public void on(ReservedCustomerBalanceFailedEvent event) {}

  @EventSourcingHandler
  public void on(CompensatedCustomerBalanceEvent event) {
    this.balance = this.balance.add(event.getAmount());
  }
}
