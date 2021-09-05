package com.springjwt1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="employee")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Pattern(message = "SSN number is not on the right format", regexp = "^(?!666|000|9\\d{2})\\d{3}-(?!00)\\d{2}-(?!0{4})\\d{4}$")
    @Column(nullable = false, unique = true, length = 11)
    @NotBlank
    private String ssn;



    @NotBlank
    @Length(message = "Lengh should be between 4 and 30 char", min = 4, max = 30)
    private String firstname;

    @NotBlank
    @Length(message = "Lengh should be between 4 and 30 char", min = 4, max =30)
    private String lastname;

    @Column(unique = true)
    @Length(message = "Length is not valid", min = 3, max = 30)
    @NotBlank
    @Size(min=3,max=30)
    private String username;


    @Length(message = "length should be between 4 and 120 character", min = 4, max = 120)
    @NotBlank
    @Size(min=4, max=120)
    private String password;

    @Email
    @NotBlank
    private String email;

    @ManyToMany
    @JoinTable(name="user_roles",
               joinColumns = @JoinColumn(name="user_id"),
               inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<UserRole> roles=new HashSet<>();

    public User(String ssn, String firstname, String lastname, String username, String password, String email) {
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }
}
