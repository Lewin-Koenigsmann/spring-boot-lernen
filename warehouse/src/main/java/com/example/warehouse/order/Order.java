package com.example.warehouse.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Aggregate Root: CascadeType.ALL speichert und löscht OrderItems automatisch
    // mit der Order. Ein eigenes OrderItemRepository ist dadurch überflüssig.
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    protected Order() {
    }

    public Order(String customerEmail) {
        this.customerEmail = customerEmail;

        this.status = OrderStatus.CREATED;
        this.totalPrice = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
    }

    // Wichtig bei bidirektionalen Beziehungen: Beide Seiten im Java-Code
    // synchronisieren (item.setOrder(this)), sonst fehlt der Fremdschlüssel.
    public void addItem(OrderItem item) {
        this.items.add(item);
        item.setOrder(this);

        BigDecimal posPrice = item.getPriceAtPurchase().multiply(BigDecimal.valueOf(item.getQuantity()));
        this.totalPrice = this.totalPrice.add(posPrice);
    }

    public Long getId() {
        return id;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItem> getItems() {
        return items;
    }

}
