package com.uuhnaut69.inventory.api;

import com.uuhnaut69.inventory.query.Product;
import com.uuhnaut69.inventory.query.type.FindAll;
import com.uuhnaut69.inventory.query.type.FindByProductId;
import lombok.RequiredArgsConstructor;
import org.axonframework.extensions.reactor.queryhandling.gateway.ReactorQueryGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductQueryController {

  private final ReactorQueryGateway reactorQueryGateway;

  @GetMapping
  public Mono<List<Product>> findAll() {
    return reactorQueryGateway.query(
        new FindAll(), ResponseTypes.multipleInstancesOf(Product.class));
  }

  @GetMapping("/{productId}")
  public Mono<Product> findByProductId(@PathVariable String productId) {
    return reactorQueryGateway.query(new FindByProductId(productId), Product.class);
  }
}
