package com.uuhnaut69.order.saga;

import com.uuhnaut69.common.ordersaga.*;
import com.uuhnaut69.order.command.OrderStatus;
import com.uuhnaut69.order.core.CreatedOrderEvent;
import com.uuhnaut69.order.core.UpdateOrderStatusCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.axonframework.modelling.saga.SagaLifecycle.associateWith;
import static org.axonframework.modelling.saga.SagaLifecycle.end;

@Saga
@Slf4j
public class OrderManagementSaga {

  @Autowired private transient CommandGateway commandGateway;

  private String productId;

  private int quantity;

  private String customerId;

  private BigDecimal totalPrice;

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  public void handle(CreatedOrderEvent event) {
    log.debug("[SAGA][ORDER-MANAGEMENT][#{}]: Start new order saga management", event.getOrderId());
    this.productId = event.getProductId();
    this.quantity = event.getQuantity();
    this.totalPrice = event.getPrice().multiply(BigDecimal.valueOf(event.getQuantity()));
    this.customerId = event.getCustomerId();

    associateWith("customerId", event.getCustomerId());
    commandGateway.send(
        new ReserveCustomerBalanceCommand(
            event.getCustomerId(), event.getOrderId(), this.totalPrice));
  }

  @SagaEventHandler(associationProperty = "customerId")
  public void handle(ReservedCustomerBalanceSuccessfullyEvent event) {
    log.debug(
        "[SAGA][ORDER-MANAGEMENT][#{}]: Reserved customer's balance successfully",
        event.getOrderId());
    associateWith("productId", productId);
    commandGateway.send(new ReserveProductStockCommand(productId, event.getOrderId(), quantity));
  }

  @SagaEventHandler(associationProperty = "customerId")
  public void handle(ReservedCustomerBalanceFailedEvent event) {
    log.debug(
        "[SAGA][ORDER-MANAGEMENT][#{}]: Reserved customer's balance failed", event.getOrderId());
    commandGateway.send(new UpdateOrderStatusCommand(event.getOrderId(), OrderStatus.CANCELED));
    end();
    log.debug("[SAGA][ORDER-MANAGEMENT][#{}]: End saga", event.getOrderId());
  }

  @SagaEventHandler(associationProperty = "productId")
  public void handle(ReservedProductStockSuccessfullyEvent event) {
    log.debug(
        "[SAGA][ORDER-MANAGEMENT][#{}]: Reserved product's stock successfully", event.getOrderId());
    commandGateway.send(new UpdateOrderStatusCommand(event.getOrderId(), OrderStatus.CONFIRMED));
    end();
    log.debug("[SAGA][ORDER-MANAGEMENT][#{}]: End saga", event.getOrderId());
  }

  @SagaEventHandler(associationProperty = "productId")
  public void handle(ReservedProductStockFailedEvent event) {
    log.debug("[SAGA][ORDER-MANAGEMENT][#{}]: Reserved product's stock failed", event.getOrderId());
    commandGateway.send(new CompensateCustomerBalanceCommand(customerId, totalPrice));
    commandGateway.send(new UpdateOrderStatusCommand(event.getOrderId(), OrderStatus.CANCELED));
    end();
    log.debug("[SAGA][ORDER-MANAGEMENT][#{}]: End saga", event.getOrderId());
  }
}
