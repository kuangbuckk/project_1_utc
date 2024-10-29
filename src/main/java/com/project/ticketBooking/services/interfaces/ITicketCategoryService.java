package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.TicketCategoryDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.TicketCategory;

import java.util.List;

public interface ITicketCategoryService {
    List<TicketCategory> getAllTicketCategories();
    TicketCategory getTicketCategoryById(Long id) throws DataNotFoundException;
    TicketCategory createTicketCategory(TicketCategoryDTO ticketCategoryDTO) throws DataNotFoundException;
    TicketCategory updateTicketCategory(Long id, TicketCategoryDTO ticketCategoryDTO) throws DataNotFoundException;
    void deleteTicketCategory(Long id);
}
