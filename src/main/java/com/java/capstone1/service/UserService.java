package com.java.capstone1.service;

import com.java.capstone1.dto.UserRequest;
import com.java.capstone1.model.User;
import com.java.capstone1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder encoder;



    public User createUser(UserRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole(req.getRole());
       return userRepository.save(user);

    }
    public List<User> show(){
        return userRepository.findAll();
    }

    public User updateStatus(String id, boolean active) {

        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if ("ADMIN".equals(targetUser.getRole()) && !active) {
            throw new RuntimeException("Admin users cannot be deactivated");
        }

        targetUser.setActive(active);
        return userRepository.save(targetUser);
    }
}
