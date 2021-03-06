package com.uuhnaut69.customer.api;

import com.uuhnaut69.customer.api.request.CustomerRequest;
import com.uuhnaut69.customer.core.CreateCustomerCommand;
import com.uuhnaut69.customer.core.DepositCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerCommandController {

  private final CommandGateway commandGateway;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompletableFuture<CreateCustomerCommand> createCustomer(
      @RequestBody @Valid CustomerRequest customerRequest) {
    return commandGateway.send(
        new CreateCustomerCommand(UUID.randomUUID().toString(), customerRequest.username()));
  }

  @PutMapping("/{customerId}/deposit/{balance}")
  public CompletableFuture<DepositCommand> deposit(
      @PathVariable String customerId, @PathVariable BigDecimal balance) {
    return commandGateway.send(new DepositCommand(customerId, balance));
  }
}
