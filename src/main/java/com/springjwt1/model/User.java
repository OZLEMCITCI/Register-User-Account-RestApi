package com.springjwt1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="employee")
@NoArgsConstructor
public class User {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

   // @Pattern(message = "Please enter a valid SSN number", regexp = "^(?!666|000|9\\\\d{2})\\\\d{3}-(?!00)\\\\d{2}-(?!0{4})\\\\d{4}$")
  @Column(nullable = false, unique = true)
//   @NotNull(message = "Ssn can not be null")
    private String ssn;



//    @NotBlank(message = "Please Enter the First Name")
//    @Pattern(message = "Please enter a valid name", regexp = "^[a-zA-Z][a-zA-Z ]*$") //^[a-zA-Z]+$
//    @Length(message = "Length should be between 2 and 30", min = 2, max = 30)
//    @Column(nullable = false, length = 20)
    private String firstname;

//    @NotBlank(message = "Please Enter the Last name")
//    @Pattern(message = "Please enter a valid Last name", regexp = "^[a-zA-Z][a-zA-Z ]*$") //^[a-zA-Z]+$
//    @Length(message = "Length should be between 2 and 30", min = 2, max = 30)
//    @Column(nullable = false, length = 20)
    private String lastname;

//    @Column(unique = true)
//    @Length(message = "Length is not valid", min = 3, max = 30)
//    @NotBlank
//    @Size(min=3,max=30)
    private String username;


    @Length(message = "length should be between 4 and 120 character", min = 4, max = 120)
    @NotBlank
    @Size(min=4, max=120)
    private String password;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String mobilePhoneNumber;

    @Column(nullable = false)
    private String address;

    @ManyToMany
    @JoinTable(name="user_roles",
               joinColumns = @JoinColumn(name="user_id"),
               inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<UserRole> roles=new HashSet<>();

    public User(String ssn, String firstname, String lastname, String username, String password, String email,String address,String mobilePhoneNumber) {
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address=address;
        this.mobilePhoneNumber=mobilePhoneNumber;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }
}
