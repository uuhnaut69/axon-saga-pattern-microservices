package com.uuhnaut69.customer.api;

import com.uuhnaut69.customer.query.Customer;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerQueryController {

  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<Customer>> findAllCustomer() {
    return queryGateway.query(
        "findAllCustomer", null, ResponseTypes.multipleInstancesOf(Customer.class));
  }

  @GetMapping("/{customerId}")
  public CompletableFuture<Customer> findByCustomerId(@PathVariable UUID customerId) {
    return queryGateway.query(
        "findByCustomerId", customerId, ResponseTypes.instanceOf(Customer.class));
  }
}
