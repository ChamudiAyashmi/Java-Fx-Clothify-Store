package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    private String paymentId;
    private double cash;
    private double balance;
    private String date;
    private boolean isPayByCash;
    private String orderId;
}
