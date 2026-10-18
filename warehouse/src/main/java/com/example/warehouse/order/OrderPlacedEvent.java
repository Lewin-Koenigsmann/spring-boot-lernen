package com.example.warehouse.order;

import java.math.BigDecimal;

public record OrderPlacedEvent(Long orderId, String customerEmail, BigDecimal totalPrice) {

}
