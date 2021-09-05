package com.springjwt1.service;

import com.springjwt1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.springjwt1.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userDetailsService")
public class UserServiceUserDetails implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user= userRepository.findByUsername(s)
                .orElseThrow(()->new UsernameNotFoundException("We could not find the user"));
        return UserServiceImpl.copyUser(user);

    }
}
