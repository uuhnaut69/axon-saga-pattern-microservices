package com.uuhnaut69.order.api;

import com.uuhnaut69.order.api.request.PlaceOrderRequest;
import com.uuhnaut69.order.core.CreateOrderCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderCommandController {

  private final CommandGateway commandGateway;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CompletableFuture<CreateOrderCommand> placeOrder(
      @RequestBody @Valid PlaceOrderRequest placeOrderRequest) {
    return commandGateway.send(
        new CreateOrderCommand(
            UUID.randomUUID().toString(),
            placeOrderRequest.customerId(),
            placeOrderRequest.productId(),
            placeOrderRequest.quantity(),
            placeOrderRequest.price()));
  }
}
