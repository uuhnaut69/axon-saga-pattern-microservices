package com.uuhnaut69.order.query;

import com.uuhnaut69.order.command.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class Order {

  @Id private UUID id;

  @Column(nullable = false)
  private Timestamp orderedDate;

  @Column(nullable = false)
  private String customerId;

  @Column(nullable = false)
  private String productId;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private BigDecimal price;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatus status;
}
