package com.java.capstone1.dto;

import lombok.Data;

@Data
public class DealResponse {
    private String id;
    private String clientName;
    private String dealType;
    private String sector;
    private String currentStage;
    private String summary;
}