package com.uuhnaut69.customer.query;

import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.customer.core.AddedCreditEvent;
import com.uuhnaut69.customer.core.CreatedCustomerEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerProjection {

  private final CustomerRepository customerRepository;

  @EventHandler
  @Transactional
  public void handle(CreatedCustomerEvent createdCustomerEvent) {
    log.info("Received created customer event {}", createdCustomerEvent);
    var customer =
        new Customer(
            UUID.fromString(createdCustomerEvent.getCustomerId()),
            createdCustomerEvent.getUsername(),
            createdCustomerEvent.getCredit());
    customerRepository.save(customer);
  }

  @EventHandler
  @Transactional
  public void handle(AddedCreditEvent addedCreditEvent) {
    log.info("Received added credit event {}", addedCreditEvent);
    var customer = findById(UUID.fromString(addedCreditEvent.getCustomerId()));
    customer.setCredit(addedCreditEvent.getCredit());
    customerRepository.save(customer);
  }

  @QueryHandler(queryName = "findAllCustomer")
  public List<Customer> findAllCustomer() {
    return customerRepository.findAll();
  }

  @QueryHandler(queryName = "findByCustomerId")
  public Customer findAllCustomer(UUID customerId) {
    return findById(customerId);
  }

  private Customer findById(UUID customerId) {
    return customerRepository
        .findById(customerId)
        .orElseThrow(
            () -> new NotFoundException(String.format("Customer %s not found", customerId)));
  }
}
