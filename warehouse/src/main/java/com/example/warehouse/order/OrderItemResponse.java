package com.example.warehouse.order;

import java.math.BigDecimal;

public record OrderItemResponse(Long id, String sku, int quantity, BigDecimal priceAtPurchase) {

}
