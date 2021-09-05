package com.springjwt1.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springjwt1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserServiceImpl implements UserDetails {
   private static final long serialVersionUID=1L;

    private Long id;
    private String ssn;
    private String firstname;
    private String lastname;
    private String username;

    @JsonIgnore
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority>authorities;

    public UserServiceImpl(Long id, String ssn, String firstname, String lastname, String username, String password, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    public static UserServiceImpl copyUser(User user){
        List<GrantedAuthority> authorityList=user.getRoles().stream()
                .map((role)->new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toList());

        return new UserServiceImpl(
                user.getId(),
                user.getSsn(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),authorityList);
    }

    public Long getId() {
        return id;
    }

    public String getSsn() {
        return ssn;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
