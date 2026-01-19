package com.java.capstone1.model;

import lombok.Data;

import java.time.Instant;

@Data
public class DealNote {
    private String userId;
    private String note;
    private Instant timestamp = Instant.now();
    public DealNote() {
    }

    public DealNote(String userId, String note, Instant timestamp) {
        this.userId = userId;
        this.note = note;
        this.timestamp = timestamp;
    }
}

