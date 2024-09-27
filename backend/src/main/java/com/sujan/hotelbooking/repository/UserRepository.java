package com.sujan.hotelbooking.repository;

import com.sujan.hotelbooking.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    // This method checks if a user exists with the given email.
    boolean existsByEmail(String email);

    // This method retrieves a user by email.
    Optional<Users> findByEmail(String email);
}
