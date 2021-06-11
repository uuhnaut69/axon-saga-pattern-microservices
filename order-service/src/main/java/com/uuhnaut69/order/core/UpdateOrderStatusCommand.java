package com.uuhnaut69.order.core;

import com.uuhnaut69.order.command.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusCommand {

  @TargetAggregateIdentifier private String orderId;

  private OrderStatus status;
}
