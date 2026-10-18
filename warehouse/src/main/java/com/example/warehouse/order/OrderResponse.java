package com.example.warehouse.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(Long id, String customerEmail, OrderStatus status, BigDecimal totalPrice, LocalDateTime createdDateTime,
    List<OrderItemResponse> items) {

}
