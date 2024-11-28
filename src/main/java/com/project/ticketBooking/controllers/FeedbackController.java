package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.FeedbackDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Feedback;
import com.project.ticketBooking.services.FeedbackService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/feedbacks")
@AllArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Feedback>> getAllFeedbacks() {
        return ResponseEntity.ok(feedbackService.getAllFeedbacks());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getFeedbackById(Long id) {
        try {
            return ResponseEntity.ok(feedbackService.getFeedbackById(id));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createFeedback(
            @Valid @RequestBody FeedbackDTO feedbackDTO
            ) {
        try {
            return ResponseEntity.ok(feedbackService.createFeedback(feedbackDTO));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateFeedback(
            @PathVariable("id") Long feedbackId,
            @RequestParam("status") String status
            ) {
        try {
            return ResponseEntity.ok(feedbackService.updateFeedback(feedbackId, status));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteFeedback(
            @PathVariable("id") Long feedbackId
            ) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok("Feedback deleted successfully");
    }
}
