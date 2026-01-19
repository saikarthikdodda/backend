package com.java.capstone1.initializer;

import com.java.capstone1.model.User;
import com.java.capstone1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("hello").isEmpty()) {

            User user = new User();
            user.setUsername("hello");
            user.setEmail("hello@gmail.com");
            user.setPassword(passwordEncoder.encode("hello@123")); // IMPORTANT
            user.setRole("ADMIN");
            user.setActive(true);

            userRepository.save(user);

            System.out.println("✅ Default admin user created");
        } else {
            System.out.println("ℹ️ Admin user already exists");
        }
    }
}
