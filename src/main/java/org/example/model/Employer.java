package org.example.model;

import lombok.*;

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
    private String dateOfBirth;
    private String address;
    private String bankAccNo;
    private String bankBranch;
    private String contactNo;

}
