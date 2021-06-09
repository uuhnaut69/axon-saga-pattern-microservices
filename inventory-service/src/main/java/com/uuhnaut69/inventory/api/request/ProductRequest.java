package com.uuhnaut69.inventory.api.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record ProductRequest(@NotBlank String name, String description, @NotNull BigDecimal price, @Min(0) int stock) {
}
