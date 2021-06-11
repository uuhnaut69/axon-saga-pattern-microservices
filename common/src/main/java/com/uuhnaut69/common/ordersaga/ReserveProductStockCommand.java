package com.uuhnaut69.common.ordersaga;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductStockCommand {

  @TargetAggregateIdentifier private String productId;

  private String orderId;

  private int quantity;
}
