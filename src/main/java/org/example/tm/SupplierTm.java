package org.example.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierTm extends RecursiveTreeObject<SupplierTm> {
    private String supplierId;
    private String title;
    private String supplierName;
    private String contact;
    private String company;
    private String email;
    private JFXButton btnDelete;


}
