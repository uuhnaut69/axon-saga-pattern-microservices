package com.uuhnaut69.customer.query;

import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.common.ordersaga.CompensatedCustomerBalanceEvent;
import com.uuhnaut69.common.ordersaga.ReservedCustomerBalanceSuccessfullyEvent;
import com.uuhnaut69.customer.core.CreatedCustomerEvent;
import com.uuhnaut69.customer.core.DepositedEvent;
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
  public void handle(CreatedCustomerEvent event) {
    log.debug("Received created customer event {}", event);
    var customer =
        new Customer(
            UUID.fromString(event.getCustomerId()), event.getUsername(), event.getBalance());
    customerRepository.save(customer);
  }

  @EventHandler
  @Transactional
  public void handle(DepositedEvent event) {
    log.debug("Received deposited balance event {}", event);
    var customer = findById(UUID.fromString(event.getCustomerId()));
    customer.setBalance(customer.getBalance().add(event.getBalance()));
    customerRepository.save(customer);
  }

  @EventHandler
  @Transactional
  public void handle(ReservedCustomerBalanceSuccessfullyEvent event) {
    log.debug("Received reserve balance successfully event {}", event);
    var customer = findById(UUID.fromString(event.getCustomerId()));
    customer.setBalance(customer.getBalance().subtract(event.getTotalPrice()));
    customerRepository.save(customer);
  }

  @EventHandler
  @Transactional
  public void handle(CompensatedCustomerBalanceEvent event) {
    log.debug("Received compensated balance event {}", event);
    var customer = findById(UUID.fromString(event.getCustomerId()));
    customer.setBalance(customer.getBalance().add(event.getAmount()));
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
