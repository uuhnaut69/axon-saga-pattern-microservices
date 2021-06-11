package com.uuhnaut69.inventory.command;

import com.uuhnaut69.common.ordersaga.ReserveProductStockCommand;
import com.uuhnaut69.common.ordersaga.ReservedProductStockFailedEvent;
import com.uuhnaut69.common.ordersaga.ReservedProductStockSuccessfullyEvent;
import com.uuhnaut69.inventory.core.CreateProductCommand;
import com.uuhnaut69.inventory.core.CreatedProductEvent;
import com.uuhnaut69.inventory.core.UpdateProductCommand;
import com.uuhnaut69.inventory.core.UpdatedProductEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class ProductAggregate {

  @AggregateIdentifier private String productId;

  private String name;

  private String description;

  private BigDecimal price;

  private int stock;

  @CommandHandler
  public ProductAggregate(CreateProductCommand command) {
    apply(
        new CreatedProductEvent(
            command.getProductId(),
            command.getName(),
            command.getDescription(),
            command.getPrice(),
            command.getStock()));
  }

  @CommandHandler
  public void handle(UpdateProductCommand command) {
    apply(
        new UpdatedProductEvent(
            command.getProductId(),
            command.getName(),
            command.getDescription(),
            command.getPrice(),
            command.getStock()));
  }

  @CommandHandler
  public void handle(ReserveProductStockCommand command) {
    if (stock < command.getQuantity()) {
      apply(
          new ReservedProductStockFailedEvent(
              command.getProductId(), command.getOrderId(), command.getQuantity()));
    } else {
      apply(
          new ReservedProductStockSuccessfullyEvent(
              command.getProductId(), command.getOrderId(), command.getQuantity()));
    }
  }

  @EventSourcingHandler
  public void on(CreatedProductEvent event) {
    this.productId = event.getProductId();
    this.name = event.getName();
    this.description = event.getDescription();
    this.price = event.getPrice();
    this.stock = event.getStock();
  }

  @EventSourcingHandler
  public void on(UpdatedProductEvent event) {
    this.productId = event.getProductId();
    this.name = event.getName();
    this.description = event.getDescription();
    this.price = event.getPrice();
    this.stock = event.getStock();
  }

  @EventSourcingHandler
  public void on(ReservedProductStockSuccessfullyEvent event) {
    this.stock = this.stock - event.getQuantity();
  }

  @EventSourcingHandler
  public void on(ReservedProductStockFailedEvent event) {}
}
