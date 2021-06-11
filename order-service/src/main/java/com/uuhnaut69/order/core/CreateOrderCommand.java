package com.uuhnaut69.order.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderCommand {

  @TargetAggregateIdentifier private String orderId;

  private String customerId;

  private String productId;

  private int quantity;

  private BigDecimal price;
}
