package com.springjwt1.repository;

import com.springjwt1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);
    Boolean existsBySsn(String ssn);
    Boolean existsByEmail(String email);
    Optional<User> findByUsername(String username);
}