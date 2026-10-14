package com.example.warehouse.order;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table (name = "order_items")
public class OrderItem {
     
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String sku;

    @Column (nullable = false)
    private int quantity;

    @Column (nullable = false)
    private BigDecimal priceAtPurchase;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "order_id", nullable = false)
    private Order order;

    protected OrderItem() {
    }

    public OrderItem(String sku, int quantity, BigDecimal priceAtPurchase) {
        this.sku = sku;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public Order getOrder() {
        return order;
    }

    void setOrder(Order order) {
        this.order = order;
    }
  
}
