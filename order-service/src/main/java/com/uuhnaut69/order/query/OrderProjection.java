package com.uuhnaut69.order.query;

import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.order.command.OrderStatus;
import com.uuhnaut69.order.core.CreatedOrderEvent;
import com.uuhnaut69.order.core.UpdatedOrderStatusEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderProjection {

  private final OrderRepository orderRepository;

  @EventHandler
  @Transactional
  public void handle(CreatedOrderEvent event) {
    var order =
        new Order(
            UUID.fromString(event.getOrderId()),
            Timestamp.from(event.getOrderedDate()),
            event.getCustomerId(),
            event.getProductId(),
            event.getQuantity(),
            event.getPrice(),
            OrderStatus.PROCESSING);
    orderRepository.save(order);
  }

  @EventHandler
  @Transactional
  public void handle(UpdatedOrderStatusEvent event) {
    var order = findById(event.getOrderId());
    order.setStatus(event.getStatus());
    orderRepository.save(order);
  }

  private Order findById(String orderId) {
    return orderRepository
        .findById(UUID.fromString(orderId))
        .orElseThrow(() -> new NotFoundException(String.format("Order %s not found", orderId)));
  }
}
