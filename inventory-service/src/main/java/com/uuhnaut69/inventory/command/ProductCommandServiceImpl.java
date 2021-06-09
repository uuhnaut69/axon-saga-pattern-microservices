package com.uuhnaut69.inventory.command;

import com.uuhnaut69.inventory.api.request.ProductRequest;
import com.uuhnaut69.inventory.core.CreateProductCommand;
import com.uuhnaut69.inventory.core.UpdateProductCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.commandhandling.gateway.ReactorCommandGateway;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductCommandServiceImpl implements ProductCommandService {

  private final ReactorCommandGateway reactorCommandGateway;

  @Override
  public Mono<CreateProductCommand> createProduct(ProductRequest productRequest) {
    var createProductCommand =
        new CreateProductCommand(
            UUID.randomUUID().toString(),
            productRequest.name(),
            productRequest.description(),
            productRequest.price(),
            productRequest.stock());
    return reactorCommandGateway.send(createProductCommand);
  }

  @Override
  public Mono<UpdateProductCommand> updateProduct(String productId, ProductRequest productRequest) {
    var updateProductCommand =
        new UpdateProductCommand(
            productId,
            productRequest.name(),
            productRequest.description(),
            productRequest.price(),
            productRequest.stock());
    return reactorCommandGateway.send(updateProductCommand);
  }
}
