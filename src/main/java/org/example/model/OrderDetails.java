package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetails {
    private String itemCode;
    private String orderId;
    private int qty;
    private double unitPrice;
    private double totalProfit;
    private double discount;

}
