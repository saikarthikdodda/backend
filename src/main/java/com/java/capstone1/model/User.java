package com.java.capstone1.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.Instant;

@Document("bank")
@Data
public class User {

    @Id
    private String id;

    private String username;
    private String email;
    private String password;

    private String role;
    private boolean active = true;

    private Instant createdAt = Instant.now();
}