package com.example.warehouse.inventory;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateProductRequest(
                @NotBlank String sku,

                @NotBlank @Size(max = 150) String name,

                @NotNull @Positive BigDecimal price,

                @PositiveOrZero int stock) {

}
