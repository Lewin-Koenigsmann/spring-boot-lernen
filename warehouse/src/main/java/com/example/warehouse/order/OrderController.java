package com.example.warehouse.order;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController 
@RequestMapping ("/api/orders")
public class OrderController {

    private final OrderService orderService;

        public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping 
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody CreateOrderRequest request)
    {
        OrderResponse placeOrder = orderService.placeOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(placeOrder);
    }

    @GetMapping
    public List<OrderResponse> getOrdersByCustomerEmail(@RequestParam String customerEmail)
    {
        return orderService.getOrdersByCustomer(customerEmail);
    }
}
