package com.example.warehouse.order;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.warehouse.inventory.Product;
import com.example.warehouse.inventory.ProductRepository;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository,
            ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    // Atomare Transaktion: Scheitert der Bestand beim zweiten Artikel, rollt
    // Spring auch die Bestandsreduzierung des ersten Artikels komplett zurück.
    @Transactional(readOnly = false)
    public OrderResponse placeOrder(CreateOrderRequest request) {
        Order newOrder = new Order(request.customerEmail());

        for (OrderItemRequest orderItemRequest : request.items()) {
            Product product = productRepository.findBySku(orderItemRequest.sku())
                    .orElseThrow(() -> new NoSuchElementException(
                            "Produkt mit SKU " + orderItemRequest.sku() + " nicht gefunden!"));
            product.reduceStock(orderItemRequest.quantity());

            OrderItem orderItem = new OrderItem(product.getSku(), orderItemRequest.quantity(), product.getPrice());
            newOrder.addItem(orderItem);
        }

        Order saved = orderRepository.save(newOrder);

        // Lose Kopplung im Monolithen: Das Order-Modul feuert nur ein Event ab,
        // ohne das Notification-Modul direkt zu kennen oder davon abzuhängen.
        eventPublisher
                .publishEvent(new OrderPlacedEvent(saved.getId(), saved.getCustomerEmail(), saved.getTotalPrice()));
        return this.toOrderResponse(saved);
    }

    public List<OrderResponse> getOrdersByCustomer(String email) {
        return orderRepository.findByCustomerEmail(email).stream().map(this::toOrderResponse).toList();
    }

    private OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getCustomerEmail(), order.getStatus(), order.getTotalPrice(),
                order.getCreatedAt(),
                order.getItems().stream().map(this::toOrderItemResponse).toList());
    }

    private OrderItemResponse toOrderItemResponse(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.getId(), orderItem.getSku(), orderItem.getQuantity(),
                orderItem.getPriceAtPurchase());
    }
}
