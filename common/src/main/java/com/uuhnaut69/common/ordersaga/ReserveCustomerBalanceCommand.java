package com.uuhnaut69.common.ordersaga;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveCustomerBalanceCommand {

  @TargetAggregateIdentifier private String customerId;

  private String orderId;

  private BigDecimal totalPrice;
}
