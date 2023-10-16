package org.example.model;

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
