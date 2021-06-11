package com.uuhnaut69.common.ordersaga;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompensatedCustomerBalanceEvent {

  private String customerId;

  private BigDecimal amount;
}
