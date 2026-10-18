package com.example.warehouse.order;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record CreateOrderRequest(
    @NotBlank 
    @Email 
    String customerEmail,

    @NotEmpty 
    @Valid 
    List<OrderItemRequest> items
) {

}
