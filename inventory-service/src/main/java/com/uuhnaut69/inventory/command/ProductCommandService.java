package com.uuhnaut69.inventory.command;

import com.uuhnaut69.inventory.api.request.ProductRequest;
import com.uuhnaut69.inventory.core.CreateProductCommand;
import com.uuhnaut69.inventory.core.UpdateProductCommand;
import reactor.core.publisher.Mono;

public interface ProductCommandService {

  Mono<CreateProductCommand> createProduct(ProductRequest productRequest);

  Mono<UpdateProductCommand> updateProduct(String productId, ProductRequest productRequest);
}
