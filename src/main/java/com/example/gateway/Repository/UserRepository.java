package com.example.gateway.Repository;

import com.example.gateway.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public UserDetails findByEmail(String email);

    public User findByName(String username);
}
