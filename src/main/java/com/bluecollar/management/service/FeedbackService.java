package com.bluecollar.management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluecollar.management.dto.FeedbackResponseDTO;
import com.bluecollar.management.entity.Feedback;
import com.bluecollar.management.entity.WorkRequest;
import com.bluecollar.management.entity.Worker;
import com.bluecollar.management.entity.enums.WorkRequestStatus;
import com.bluecollar.management.repository.FeedbackRepository;
import com.bluecollar.management.repository.WorkRequestRepository;
import com.bluecollar.management.repository.WorkerRepository;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final WorkRequestRepository workRequestRepository;
    private final WorkerRepository workerRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           WorkRequestRepository workRequestRepository,
                           WorkerRepository workerRepository) {
        this.feedbackRepository = feedbackRepository;
        this.workRequestRepository = workRequestRepository;
        this.workerRepository = workerRepository;
    }

    @Transactional
    public FeedbackResponseDTO addFeedback(Long requestId, Integer rating, String comment) {

        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        WorkRequest request = workRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Work request not found"));

        if (request.getStatus() != WorkRequestStatus.COMPLETED) {
            throw new RuntimeException("Feedback allowed only after COMPLETED work");
        }

        Worker worker = request.getWorker();

        Feedback feedback = new Feedback();
        feedback.setWorkRequest(request);
        feedback.setWorker(worker);
        feedback.setRating(rating);
        feedback.setComment(comment);
        feedback.setCreatedAt(LocalDateTime.now());

        Feedback savedFeedback = feedbackRepository.save(feedback);

        updateWorkerRating(worker);

        return mapToResponseDTO(savedFeedback);
    }

    private void updateWorkerRating(Worker worker) {

        List<Feedback> feedbackList = feedbackRepository.findByWorker(worker);

        double avgRating = feedbackList.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        worker.setRating(Math.round(avgRating * 10.0) / 10.0);
        workerRepository.save(worker);
    }

    private FeedbackResponseDTO mapToResponseDTO(Feedback feedback) {

        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setFeedbackId(feedback.getId());
        dto.setWorkRequestId(feedback.getWorkRequest().getId());
        dto.setWorkerId(feedback.getWorker().getId());
        dto.setRating(feedback.getRating());
        dto.setComment(feedback.getComment());
        dto.setCreatedAt(feedback.getCreatedAt());

        return dto;
    }
}
