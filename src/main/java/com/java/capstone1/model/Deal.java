package com.java.capstone1.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document("deals")
@Data
public class Deal {

    @Id
    private String id;
    private String clientName;
    private String dealType;
    private String sector;
    private Long dealValue; // ADMIN only
    private String currentStage;
    private String summary;
    private List<DealNote> notes = new ArrayList<>();
    private String createdBy;
    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
