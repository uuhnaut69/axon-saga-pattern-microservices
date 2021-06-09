package com.uuhnaut69.inventory.api;

import com.uuhnaut69.inventory.api.request.ProductRequest;
import com.uuhnaut69.inventory.command.ProductCommandService;
import com.uuhnaut69.inventory.core.CreateProductCommand;
import com.uuhnaut69.inventory.core.UpdateProductCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductCommandController {

  private final ProductCommandService productCommandService;

  @PostMapping
  public Mono<CreateProductCommand> createProduct(
      @RequestBody @Valid ProductRequest productRequest) {
    return productCommandService.createProduct(productRequest);
  }

  @PutMapping("/{productId}")
  public Mono<UpdateProductCommand> updateProduct(
      @PathVariable String productId, @RequestBody @Valid ProductRequest productRequest) {
    return productCommandService.updateProduct(productId, productRequest);
  }
}
