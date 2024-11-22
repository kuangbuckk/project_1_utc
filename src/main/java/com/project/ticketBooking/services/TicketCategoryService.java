package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.TicketCategoryDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.TicketCategory;
import com.project.ticketBooking.repositories.EventRepository;
import com.project.ticketBooking.repositories.TicketCategoryRepository;
import com.project.ticketBooking.services.interfaces.ITicketCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TicketCategoryService implements ITicketCategoryService {
    private final TicketCategoryRepository ticketCategoryRepository;
    private final EventRepository eventRepository;

    @Override
    public List<TicketCategory> getAllTicketCategories() {
        return ticketCategoryRepository.findAll();
    }

    @Override
    public TicketCategory getTicketCategoryById(Long id) throws DataNotFoundException {
        return ticketCategoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket Category not found"));
    }

    @Override
    public TicketCategory createTicketCategory(TicketCategoryDTO ticketCategoryDTO) throws DataNotFoundException {
        Event existingEvent = eventRepository.findById(ticketCategoryDTO.getEventId())
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        TicketCategory ticketCategory = TicketCategory.builder()
                .categoryName(ticketCategoryDTO.getCategoryName())
                .price(ticketCategoryDTO.getPrice())
                .remainingCount(ticketCategoryDTO.getRemainingCount())
                .event(existingEvent)
                .build();
        return ticketCategoryRepository.save(ticketCategory);
    }

    @Override
    public TicketCategory updateTicketCategory(Long id, TicketCategoryDTO ticketCategoryDTO) throws DataNotFoundException {
        TicketCategory ticketCategory = ticketCategoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket Category not found"));

        Event existingEvent = eventRepository.findById(ticketCategoryDTO.getEventId())
                .orElseThrow(() -> new DataNotFoundException("Event not found"));

        ticketCategory.setCategoryName(ticketCategoryDTO.getCategoryName());
        ticketCategory.setPrice(ticketCategoryDTO.getPrice());
        ticketCategory.setRemainingCount(ticketCategoryDTO.getRemainingCount());
//        ticketCategory.setEvent(existingEvent);
        return ticketCategoryRepository.save(ticketCategory);
    }

    @Override
    public void deleteTicketCategory(Long id) {
        ticketCategoryRepository.deleteById(id);
    }

    @Override
    public List<TicketCategory> getTicketCategoriesByEventId(Long eventId) throws DataNotFoundException {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new DataNotFoundException("Event not found"));
        return ticketCategoryRepository.findByEventId(eventId);
    }

    @Override
    public List<TicketCategory> getTicketCategoriesByIds(List<Long> ticketCategoryIds) {
        return ticketCategoryRepository.findTicketCategoriesByIds(ticketCategoryIds);
    }

    @Override
    public boolean hasAvailableTickets(Long categoryId) {
        return ticketCategoryRepository.existsByIdAndRemainingCountGreaterThan(categoryId, 0);
    }

}