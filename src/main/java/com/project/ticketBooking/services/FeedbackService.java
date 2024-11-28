package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.FeedbackDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Feedback;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.repositories.FeedbackRepository;
import com.project.ticketBooking.repositories.UserRepository;
import com.project.ticketBooking.services.interfaces.IFeedbackService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FeedbackService implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final String FEEDBACK_NEW_STATUS = "pending";

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    public Feedback getFeedbackById(Long id) throws DataNotFoundException {
        return feedbackRepository.findById(id).orElseThrow(() ->
                new DataNotFoundException(
                        "Cannot find feedback with id: "+ id));
    }

    @Override
    public Feedback createFeedback(FeedbackDTO feedbackDTO) throws DataNotFoundException {
        Optional<User> exisitingUser = userRepository.getUserById(feedbackDTO.getUserId());
        if (!exisitingUser.isPresent()) {
            throw new DataNotFoundException("Cannot find user with id: " + feedbackDTO.getUserId());
        }
        User user = exisitingUser.get();
        Feedback newFeedback = Feedback.builder()
                .email(feedbackDTO.getEmail())
                .content(feedbackDTO.getContent())
                .type(feedbackDTO.getType())
                .status(FEEDBACK_NEW_STATUS)
                .user(user)
                .build();
        return feedbackRepository.save(newFeedback);
    }

    @Override
    public Feedback updateFeedback(Long feedbackId, String status) throws DataNotFoundException {
        Feedback existingFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new DataNotFoundException("Cannot find feedback with id: " + feedbackId));
        existingFeedback.setStatus(status);
        return feedbackRepository.save(existingFeedback);
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }
}
