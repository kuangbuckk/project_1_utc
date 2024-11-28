package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.FeedbackDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Feedback;

import java.util.List;

public interface IFeedbackService {
    List<Feedback> getAllFeedbacks();
    Feedback getFeedbackById(Long id) throws DataNotFoundException;
    Feedback createFeedback(FeedbackDTO feedbackDTO) throws DataNotFoundException;
    Feedback updateFeedback(Long feedbackId, String status) throws DataNotFoundException;
    void deleteFeedback(Long feedbackId);
}
