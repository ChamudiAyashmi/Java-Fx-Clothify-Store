package org.example.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SalesReturn {
    private String returnId;
    private String orderId;
    private double total;
    private String date;

}
