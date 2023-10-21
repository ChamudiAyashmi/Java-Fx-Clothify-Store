package org.example.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Orders {
    private String orderId;
    private String date;
    private double totalDiscount;
    private double total;
    private String empId;
    private String customerName;
    private String customerEmail;
    private String customerContact;
}
