package com.springjwt1.service;

import com.springjwt1.regres.*;
import com.springjwt1.model.ERoles;
import com.springjwt1.model.User;
import com.springjwt1.model.UserRole;
import com.springjwt1.security.JWT.JWTUtils;
import com.springjwt1.security.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.stereotype.Service;
import com.springjwt1.repository.UserRepository;
import com.springjwt1.repository.UserRoleRepository;

import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService{// implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRoleRepository userRoleRepository;





//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//     User user=userRepository.findByUsername(username)
//             .orElseThrow(()->new UsernameNotFoundException("User not found "));
//     return UserServiceImpl.copyUser(user);
//    }

   @Autowired
   AuthenticationManager authenticationManager;

   @Autowired
   JWTUtils jwtUtils;



    public ResponseEntity<?>getUserLogin(LoginRequest loginRequest){
      Authentication authentication=
              authenticationManager
              .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt= jwtUtils.jwtCreate(authentication);

      UserServiceImpl loginedUser= (UserServiceImpl) authentication.getPrincipal();

      List<String> roles= loginedUser
              .getAuthorities()
              .stream()
              .map(item->item.getAuthority())
              .collect(Collectors.toList());

      return ResponseEntity
              .ok(new JwtResponse(jwt,loginedUser.getId(),
                      loginedUser.getSsn(),
                      loginedUser.getFirstname(),
                      loginedUser.getLastname(),
                      loginedUser.getUsername(),
                      loginedUser.getEmail(),roles));

    }

    public ResponseEntity<?> registerUser(RegisterUser registerequest){

        if(userRepository.existsBySsn(registerequest.getSsn())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRequest("ssn is in use. Please try different"));
        }

        if(userRepository.existsByUsername(registerequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRequest("username is already in use"));
        }

        if(userRepository.existsByEmail(registerequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageRequest("email is already in use"));
        }

        User newUserCreate=new User(
                registerequest.getSsn(),
                registerequest.getFirstname(),
                registerequest.getLastname(),
                registerequest.getUsername(),
                passwordEncoder.encode(registerequest.getPassword()),
                registerequest.getEmail()
                );

        Set<String> registerUserRole=registerequest.getRoles();

        Set<UserRole>roles=new HashSet<>();

        if(registerUserRole==null){
           UserRole role=userRoleRepository.findByRole(ERoles.ROLE_USER)
           .orElseThrow(()->new RuntimeException("HATA: VERITABANINDA ROLE KAYITLI DEGIL")) ;
            roles.add(role);
        }else {
            registerUserRole
                    .stream()
                    .forEach(userRole -> {
                        switch (userRole.toString()){

                            case "admin":
                            UserRole adminRole=userRoleRepository.findByRole(ERoles.ROLE_ADMIN)
                                    .orElseThrow(()->new RuntimeException("HATA: VERITABANINDA ROLE KAYITLI DEGIL"));

                            roles.add(adminRole);
                            break;

                            case "employee":
                                UserRole employeeRole=userRoleRepository.findByRole(ERoles.ROLE_EMPLOYEE)
                                        .orElseThrow(()->new RuntimeException("HATA: VERITABANINDA ROLE KAYITLI DEGIL"));

                                roles.add(employeeRole);
                                break;

                            default:

                                UserRole DefaultRole=userRoleRepository.findByRole(ERoles.ROLE_USER)
                                        .orElseThrow(()->new RuntimeException("HATA: VERITABANINDA ROLE KAYITLI DEGIL")) ;
                                roles.add(DefaultRole);

                        }
                    });

            newUserCreate.setRoles(roles);
            userRepository.save(newUserCreate);
        }

        return ResponseEntity.ok(new MessageRequest("Kullanici basarili kayit edildi"));


    }
}
