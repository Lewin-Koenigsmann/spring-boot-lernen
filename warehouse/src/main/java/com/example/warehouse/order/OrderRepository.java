package com.example.warehouse.order;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Löst das N+1 Select Problem: Lädt Bestellungen und ihre Positionen in
    // einer einzigen SQL-Abfrage per LEFT JOIN statt in N Einzelabfragen.
    @EntityGraph(attributePaths = "items")
    List<Order> findByCustomerEmail(String customerEmail);
}
