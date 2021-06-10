package com.uuhnaut69.customer.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddedCreditEvent {

  private String customerId;

  private BigDecimal credit;
}
