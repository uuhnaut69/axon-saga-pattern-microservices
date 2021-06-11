package com.uuhnaut69.order.core;

import com.uuhnaut69.order.command.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedOrderStatusEvent {

  private String orderId;

  private OrderStatus status;
}
