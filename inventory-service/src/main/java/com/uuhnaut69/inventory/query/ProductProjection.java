package com.uuhnaut69.inventory.query;

import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.inventory.core.CreatedProductEvent;
import com.uuhnaut69.inventory.core.UpdatedProductEvent;
import com.uuhnaut69.inventory.query.type.FindAll;
import com.uuhnaut69.inventory.query.type.FindByProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductProjection {

  private final ProductRepository productRepository;

  @EventHandler
  public void handle(CreatedProductEvent createdProductEvent) {
    log.info("Received created product event {}", createdProductEvent);
    var product =
        new Product(
            createdProductEvent.getProductId(),
            createdProductEvent.getName(),
            createdProductEvent.getDescription(),
            createdProductEvent.getPrice(),
            createdProductEvent.getStock());
    productRepository.save(product).subscribe();
  }

  @EventHandler
  public void handle(UpdatedProductEvent updatedProductEvent) {
    log.info("Received updated product event {}", updatedProductEvent);
    var product =
        new Product(
            updatedProductEvent.getProductId(),
            updatedProductEvent.getName(),
            updatedProductEvent.getDescription(),
            updatedProductEvent.getPrice(),
            updatedProductEvent.getStock());
    productRepository.save(product).subscribe();
  }

  @QueryHandler
  public CompletableFuture<List<Product>> findAll(FindAll findAll) {
    return productRepository.findAll().collectList().toFuture();
  }

  @QueryHandler
  public CompletableFuture<Product> findByProductId(FindByProductId findByProductId) {
    return productRepository
        .findById(findByProductId.getProductId())
        .switchIfEmpty(
            Mono.error(
                new NotFoundException(
                    String.format("Product %s not found", findByProductId.getProductId()))))
        .toFuture();
  }
}
