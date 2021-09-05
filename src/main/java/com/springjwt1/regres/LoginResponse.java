package com.springjwt1.regres;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Long id;

    private String ssn;

    private String firstname;
     private String lastname;
    private String username;
    private String email;

    private List<String> roller;
}
