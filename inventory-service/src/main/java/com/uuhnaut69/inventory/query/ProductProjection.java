package com.uuhnaut69.inventory.query;

import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.inventory.core.CreatedProductEvent;
import com.uuhnaut69.inventory.core.UpdatedProductEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductProjection {

  private final ProductRepository productRepository;

  @EventHandler
  @Transactional
  public void handle(CreatedProductEvent createdProductEvent) {
    log.info("Received created product event {}", createdProductEvent);
    var product =
        new Product(
            createdProductEvent.getProductId(),
            createdProductEvent.getName(),
            createdProductEvent.getDescription(),
            createdProductEvent.getPrice(),
            createdProductEvent.getStock());
    productRepository.save(product);
  }

  @EventHandler
  @Transactional
  public void handle(UpdatedProductEvent updatedProductEvent) {
    log.info("Received updated product event {}", updatedProductEvent);
    var product =
        new Product(
            updatedProductEvent.getProductId(),
            updatedProductEvent.getName(),
            updatedProductEvent.getDescription(),
            updatedProductEvent.getPrice(),
            updatedProductEvent.getStock());
    productRepository.save(product);
  }

  @QueryHandler(queryName = "findAll")
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @QueryHandler(queryName = "findByProductId")
  public Product findByProductId(String productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException(String.format("Product %s not found", productId)));
  }
}
