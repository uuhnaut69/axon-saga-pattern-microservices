package com.uuhnaut69.inventory.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedProductEvent {

  private String productId;

  private String name;

  private String description;

  private BigDecimal price;

  private int stock;
}
