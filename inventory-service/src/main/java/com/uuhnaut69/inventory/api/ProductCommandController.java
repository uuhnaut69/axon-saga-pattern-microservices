package com.uuhnaut69.inventory.api;

import com.uuhnaut69.inventory.api.request.ProductRequest;
import com.uuhnaut69.inventory.core.CreateProductCommand;
import com.uuhnaut69.inventory.core.UpdateProductCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductCommandController {

  private final CommandGateway commandGateway;

  @PostMapping
  public CompletableFuture<CreateProductCommand> createProduct(
      @RequestBody @Valid ProductRequest productRequest) {
    return commandGateway.send(
        new CreateProductCommand(
            UUID.randomUUID().toString(),
            productRequest.name(),
            productRequest.description(),
            productRequest.price(),
            productRequest.stock()));
  }

  @PutMapping("/{productId}")
  public CompletableFuture<UpdateProductCommand> updateProduct(
      @PathVariable String productId, @RequestBody @Valid ProductRequest productRequest) {
    return commandGateway.send(
        new UpdateProductCommand(
            productId,
            productRequest.name(),
            productRequest.description(),
            productRequest.price(),
            productRequest.stock()));
  }
}
