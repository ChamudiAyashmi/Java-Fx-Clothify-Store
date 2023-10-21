package org.example.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplies {
    private String itemCode;
    private String Description;
    private int qty;
}
