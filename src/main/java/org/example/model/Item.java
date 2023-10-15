package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private String itemCode;
    private String description;
    private int qtyOnHand;
    private double sellingPrice;
    private double buyingPrice;
    private String supplierId;
    private String type;
    private String size;


}
