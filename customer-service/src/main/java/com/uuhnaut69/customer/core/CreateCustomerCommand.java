package com.uuhnaut69.customer.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerCommand {

  @TargetAggregateIdentifier private String customerId;

  private String username;
}
