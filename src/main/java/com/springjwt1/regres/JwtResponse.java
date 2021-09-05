package com.springjwt1.regres;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private String type="Bearer";
    private Long id;
    private String ssn;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private List<String> roller;

    public JwtResponse(String token, Long id, String ssn, String firstname, String lastname, String username, String email, List<String> roller) {
        this.token = token;
        this.id = id;
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.roller = roller;
    }
}
