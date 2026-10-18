package com.example.warehouse.order;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.warehouse.inventory.Product;
import com.example.warehouse.inventory.ProductRepository;

@SpringBootTest
public class OrderIntegrationTest {

    // Kein final bei @Autowired-Feldern möglich, da Spring die Abhängigkeiten
    // erst nach dem Konstruktor-Aufruf per Reflection in die Testklasse injiziert.
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldRollbackStockWhenSecondItemFails() {
        productRepository.save(new Product("LAPTOP-1", "Laptop", new BigDecimal("1000.00"), 5));
        productRepository.save(new Product("MOUSE-1", "Maus", new BigDecimal("50.00"), 1));

        CreateOrderRequest request = new CreateOrderRequest("kunde@test.de",
                List.of(new OrderItemRequest("LAPTOP-1", 2), new OrderItemRequest("MOUSE-1", 5)));

        Assertions.assertThrows(IllegalStateException.class, () -> orderService.placeOrder(request));

        // Hier .get(), da nur eine Testumgebung
        Product product1 = productRepository.findBySku("LAPTOP-1").get();

        Assertions.assertEquals(5, product1.getStock());
        Assertions.assertEquals(0, orderRepository.count());
    }

    @Test
    void shouldPlaceOrderSuccessfully() {
        productRepository.save(new Product("LAPTOP-1", "Laptop", new BigDecimal("1000.00"), 5));
        productRepository.save(new Product("MOUSE-1", "Maus", new BigDecimal("50.00"), 5));

        CreateOrderRequest request = new CreateOrderRequest("kunde@test.de",
                List.of(new OrderItemRequest("LAPTOP-1", 2), new OrderItemRequest("MOUSE-1", 1)));

        orderService.placeOrder(request);

        // Hier .get(), da nur eine Testumgebung
        Product product1 = productRepository.findBySku("LAPTOP-1").get();

        Assertions.assertEquals(3, product1.getStock());
        Assertions.assertEquals(1, orderRepository.count());
    }
}
