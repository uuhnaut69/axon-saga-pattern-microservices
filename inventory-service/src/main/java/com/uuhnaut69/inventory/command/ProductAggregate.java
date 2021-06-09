package com.uuhnaut69.inventory.command;

import com.uuhnaut69.inventory.core.CreateProductCommand;
import com.uuhnaut69.inventory.core.CreatedProductEvent;
import com.uuhnaut69.inventory.core.UpdateProductCommand;
import com.uuhnaut69.inventory.core.UpdatedProductEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ProductAggregate {

  @AggregateIdentifier private String productId;

  private String name;

  private String description;

  private BigDecimal price;

  private int stock;

  @CommandHandler
  public ProductAggregate(CreateProductCommand createProductCommand) {
    apply(
        new CreatedProductEvent(
            createProductCommand.getProductId(),
            createProductCommand.getName(),
            createProductCommand.getDescription(),
            createProductCommand.getPrice(),
            createProductCommand.getStock()));
  }

  @CommandHandler
  public void handle(UpdateProductCommand updateProductCommand) {
    apply(
        new UpdatedProductEvent(
            updateProductCommand.getProductId(),
            updateProductCommand.getName(),
            updateProductCommand.getDescription(),
            updateProductCommand.getPrice(),
            updateProductCommand.getStock()));
  }

  @EventSourcingHandler
  protected void on(CreatedProductEvent createdProductEvent) {
    this.productId = createdProductEvent.getProductId();
    this.name = createdProductEvent.getName();
    this.description = createdProductEvent.getDescription();
    this.price = createdProductEvent.getPrice();
    this.stock = createdProductEvent.getStock();
  }

  @EventSourcingHandler
  protected void on(UpdatedProductEvent updatedProductEvent) {
    this.productId = updatedProductEvent.getProductId();
    this.name = updatedProductEvent.getName();
    this.description = updatedProductEvent.getDescription();
    this.price = updatedProductEvent.getPrice();
    this.stock = updatedProductEvent.getStock();
  }

  protected ProductAggregate() {}
}
