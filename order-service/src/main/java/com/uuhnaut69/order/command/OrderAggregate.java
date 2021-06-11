package com.uuhnaut69.order.command;

import com.uuhnaut69.order.core.CreateOrderCommand;
import com.uuhnaut69.order.core.CreatedOrderEvent;
import com.uuhnaut69.order.core.UpdateOrderStatusCommand;
import com.uuhnaut69.order.core.UpdatedOrderStatusEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.time.Instant;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {

  @AggregateIdentifier private String orderId;

  private Instant orderedDate;

  private String customerId;

  private String productId;

  private int quantity;

  private BigDecimal unitPrice;

  private BigDecimal totalPrice;

  private OrderStatus status;

  @CommandHandler
  public OrderAggregate(CreateOrderCommand command) {
    apply(
        new CreatedOrderEvent(
            command.getOrderId(),
            Instant.now(),
            command.getCustomerId(),
            command.getProductId(),
            command.getQuantity(),
            command.getPrice()));
  }

  @CommandHandler
  public void handle(UpdateOrderStatusCommand command) {
    apply(new UpdatedOrderStatusEvent(command.getOrderId(), command.getStatus()));
  }

  @EventSourcingHandler
  public void on(CreatedOrderEvent event) {
    this.orderId = event.getOrderId();
    this.orderedDate = event.getOrderedDate();
    this.customerId = event.getCustomerId();
    this.productId = event.getProductId();
    this.quantity = event.getQuantity();
    this.totalPrice = event.getPrice().multiply(BigDecimal.valueOf(event.getQuantity()));
    this.status = OrderStatus.PROCESSING;
  }

  @EventSourcingHandler
  public void on(UpdatedOrderStatusEvent event) {
    this.orderId = event.getOrderId();
    this.status = event.getStatus();
  }
}
