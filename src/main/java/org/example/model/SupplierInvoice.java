package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierInvoice {
    private String invoiceId;
    private String supplierId;
    private String itemCode;
    private String date;
    private int qty;
}
