package com.example.testapp.repository;

import com.example.testapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findUserByUsername(String username);
}
