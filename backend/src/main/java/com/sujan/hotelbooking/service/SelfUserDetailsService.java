package com.sujan.hotelbooking.service;

import com.sujan.hotelbooking.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Marks this class as a service component for Spring
public class SelfUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // UserRepository instance for database access

    // Constructor injection for UserRepository
    public SelfUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Attempt to find a user by their email (which is treated as username here)
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Email not found")); // Throw exception if user is not found
    }
}
