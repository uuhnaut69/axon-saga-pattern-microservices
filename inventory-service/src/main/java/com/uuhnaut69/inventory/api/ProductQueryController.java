package com.uuhnaut69.inventory.api;

import com.uuhnaut69.inventory.query.Product;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductQueryController {

  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<Product>> findAll() {
    return queryGateway.query("findAll", null, ResponseTypes.multipleInstancesOf(Product.class));
  }

  @GetMapping("/{productId}")
  public CompletableFuture<Product> findByProductId(@PathVariable String productId) {
    return queryGateway.query("findByProductId", productId, Product.class);
  }
}
