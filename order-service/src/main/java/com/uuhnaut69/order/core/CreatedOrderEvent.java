package com.uuhnaut69.order.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedOrderEvent {

  private String orderId;

  private Instant orderedDate;

  private String customerId;

  private String productId;

  private int quantity;

  private BigDecimal price;
}
