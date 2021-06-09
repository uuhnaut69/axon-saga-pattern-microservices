package com.uuhnaut69.inventory.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductCommand {

  @TargetAggregateIdentifier private String productId;

  private String name;

  private String description;

  private BigDecimal price;

  private int stock;
}
