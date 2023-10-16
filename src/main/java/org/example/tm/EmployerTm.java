package org.example.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployerTm extends RecursiveTreeObject<EmployerTm> {
    private String empId;
    private String title;
    private String name;
    private String nic;
    private LocalDate dateOfBirth;
    private String address;
    private String contactNo;
    private String bankAccNo;
    private String bankBranch;
    private JFXButton btnDelete;

}
