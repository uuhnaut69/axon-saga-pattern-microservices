package com.uuhnaut69.common.ordersaga;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservedCustomerBalanceFailedEvent {

  private String customerId;

  private String orderId;

  private BigDecimal totalPrice;
}
