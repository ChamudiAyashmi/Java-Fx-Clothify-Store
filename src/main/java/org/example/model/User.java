package org.example.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String id;
    private String email;
    private String password;
    private int user_Type;
}
