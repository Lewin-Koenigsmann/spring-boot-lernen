package com.example.warehouse.inventory;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false, length = 150)
    private String name;

    // Niemals double/float für Geldbeträge nutzen (Rundungsfehler im Binärsystem).
    // BigDecimal garantiert exakte kaufmännische Dezimalberechnungen.
    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;

    // Optimistic Locking: Schützt vor dem Lost-Update-Problem bei zeitgleichen
    // Käufen, ohne langsame Datenbank-Locks zu verwenden.
    @Version
    private Long version;

    Product() {
    }

    public Product(String sku, String name, BigDecimal price, int stock) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Rich Domain Model: Geschäftslogik liegt direkt in der Entity. Wirft
    // IllegalStateException, wenn der aktuelle Objektzustand den Kauf verbietet.
    public void reduceStock(int quantity) {
        if (quantity <= 0)
            throw new IllegalArgumentException();
        else if (this.stock < quantity)
            throw new IllegalStateException("Nicht genügend Bestand für Produkt: " + this.name);

        this.stock -= quantity;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Long getVersion() {
        return version;
    }

}
