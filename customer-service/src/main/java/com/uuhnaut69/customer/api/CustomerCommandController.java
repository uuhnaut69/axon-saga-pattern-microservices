package com.uuhnaut69.customer.api;

import com.uuhnaut69.customer.api.request.CustomerRequest;
import com.uuhnaut69.customer.core.AddCreditCommand;
import com.uuhnaut69.customer.core.CreateCustomerCommand;
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

  @PutMapping("/{customerId}/add-credit/{credit}")
  public CompletableFuture<AddCreditCommand> addCredit(
      @PathVariable String customerId, @PathVariable BigDecimal credit) {
    return commandGateway.send(new AddCreditCommand(customerId, credit));
  }
}
