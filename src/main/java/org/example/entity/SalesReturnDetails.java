package org.example.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesReturnDetails {
    private String returnId;
    private String itemCode;
    private int qty;
    private double discount;
    private double unitPrice;
    private double amount;
}
