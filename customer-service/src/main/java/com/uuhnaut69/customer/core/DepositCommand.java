package com.uuhnaut69.customer.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepositCommand {

  @TargetAggregateIdentifier private String customerId;

  private BigDecimal balance;
}
