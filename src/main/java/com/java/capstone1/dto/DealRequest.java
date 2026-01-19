package com.java.capstone1.dto;

import lombok.Data;

@Data
public class DealRequest {
    private String clientName;
    private String dealType;
    private String sector;
    private String summary;
}

