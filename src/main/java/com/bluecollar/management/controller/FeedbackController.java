package com.bluecollar.management.controller;

import org.springframework.web.bind.annotation.*;

import com.bluecollar.management.dto.FeedbackResponseDTO;
import com.bluecollar.management.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/add")
    public FeedbackResponseDTO addFeedback(
            @RequestParam Long requestId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment) {

        return feedbackService.addFeedback(requestId, rating, comment);
    }

}
