package com.example.warehouse.notification;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.warehouse.order.OrderPlacedEvent;

@Component
public class OrderNotificationListener {

    // Feuert erst NACH erfolgreichem Datenbank-Commit (AFTER_COMMIT). Bei einem
    // Transaktions-Rollback wird niemals fälschlich eine E-Mail verschickt.
    @TransactionalEventListener
    public void onOrderPlaced(OrderPlacedEvent event) {
        System.out.println("📧 [BENACHRICHTIGUNG] Sende Bestätigungs-Mail an " + event.customerEmail()
                + " für Bestellung #" + event.orderId());
    }
}
