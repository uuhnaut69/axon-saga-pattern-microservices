package com.uuhnaut69.order.api.request;

import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public record PlaceOrderRequest(@NotBlank String customerId, @NotBlank String productId, @Min(1) int quantity,@NonNull BigDecimal price) {
}
