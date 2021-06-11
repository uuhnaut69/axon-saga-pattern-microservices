package com.uuhnaut69.inventory.query;

import com.uuhnaut69.common.exception.NotFoundException;
import com.uuhnaut69.common.ordersaga.ReservedProductStockSuccessfullyEvent;
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
  public void handle(CreatedProductEvent event) {
    log.debug("Received created product event {}", event);
    var product =
        new Product(
            event.getProductId(),
            event.getName(),
            event.getDescription(),
            event.getPrice(),
            event.getStock());
    productRepository.save(product);
  }

  @EventHandler
  @Transactional
  public void handle(UpdatedProductEvent event) {
    log.debug("Received updated product event {}", event);
    var product = findById(event.getProductId());
    product.setName(event.getName());
    product.setDescription(event.getDescription());
    product.setPrice(event.getPrice());
    product.setStock(event.getStock());
    productRepository.save(product);
  }

  @EventHandler
  @Transactional
  public void handle(ReservedProductStockSuccessfullyEvent event) {
    log.debug("Received reserved product's stock successfully event {}", event);
    var product = findById(event.getProductId());
    product.setStock(product.getStock() - event.getQuantity());
    productRepository.save(product);
  }

  @QueryHandler(queryName = "findAll")
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @QueryHandler(queryName = "findByProductId")
  public Product findByProductId(String productId) {
    return findById(productId);
  }

  private Product findById(String productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException(String.format("Product %s not found", productId)));
  }
}
