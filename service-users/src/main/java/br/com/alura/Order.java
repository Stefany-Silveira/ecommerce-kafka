package br.com.alura;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    private final String userId, orderId;
    private final BigDecimal amount;
    private final String email;

    public Order(String userId, String orderId, BigDecimal amount, String email) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.email = email;
    }
}
