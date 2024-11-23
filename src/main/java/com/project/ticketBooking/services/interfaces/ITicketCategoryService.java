package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.TicketCategoryDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.exceptions.PermissionDenyException;
import com.project.ticketBooking.models.TicketCategory;

import java.util.List;

public interface ITicketCategoryService {
    List<TicketCategory> getAllTicketCategories();
    TicketCategory getTicketCategoryById(Long id) throws DataNotFoundException;
    TicketCategory createTicketCategory(TicketCategoryDTO ticketCategoryDTO) throws DataNotFoundException;
    TicketCategory createTicketCategoryFromOrganization(
            String token, TicketCategoryDTO ticketCategoryDTO) throws DataNotFoundException;
    TicketCategory updateTicketCategory(Long id, TicketCategoryDTO ticketCategoryDTO) throws DataNotFoundException;
    TicketCategory updateTicketCategoryFromOrganization(
            String token, Long id, TicketCategoryDTO ticketCategoryDTO) throws DataNotFoundException, PermissionDenyException;
    void deleteTicketCategory(Long id);
    List<TicketCategory> getTicketCategoriesByEventId(Long eventId) throws DataNotFoundException;
    List<TicketCategory> getTicketCategoriesByIds(List<Long> ticketCategoryIds);
    boolean hasAvailableTickets(Long categoryId);
}
