package org.example.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employer {
    private String empId;
    private String title;
    private String name;
    private String nic;
    private LocalDate dateOfBirth;
    private String address;
    private String contactNo;
    private String bankAccNo;
    private String bankBranch;


}
