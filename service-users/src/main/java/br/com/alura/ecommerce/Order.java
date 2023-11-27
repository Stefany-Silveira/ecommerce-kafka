package br.com.alura.ecommerce;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

    private final String orderId;
    private final BigDecimal amount;
    private final String email;

}
