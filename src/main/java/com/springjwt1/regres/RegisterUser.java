package com.springjwt1.regres;

import com.springjwt1.model.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
public class RegisterUser {

    @NotBlank
    private String ssn;

    @NotBlank
    private String firstname;

    @NotBlank
    private String username;

    @NotBlank
    private String lastname;

    @NotBlank
    private String password;
    @NotBlank
    private String email;

    private Set<String> roles;

}
