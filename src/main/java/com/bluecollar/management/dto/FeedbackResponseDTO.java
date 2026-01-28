package com.bluecollar.management.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackResponseDTO {

    private Long feedbackId;
    private Long workRequestId;
    private Long workerId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    // getters & setters
}
