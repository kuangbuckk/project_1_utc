package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.TicketDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.Ticket;
import com.project.ticketBooking.models.TicketCategory;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.repositories.EventRepository;
import com.project.ticketBooking.repositories.TicketCategoryRepository;
import com.project.ticketBooking.repositories.TicketRepository;
import com.project.ticketBooking.repositories.UserRepository;
import com.project.ticketBooking.services.interfaces.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final TicketRepository ticketRepository;
    private final TicketCategoryRepository ticketCategoryRepository;
    private final UserRepository userRepository;

    @Override
    public List<Ticket> getTicketsByTicketCategoryId(Long ticketCategoryId) {
        return ticketRepository.findByTicketCategoryId(ticketCategoryId);
    }

    @Override
    public Ticket getTicketById(Long id) throws DataNotFoundException {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket not found"));
    }

    @Override
    public Ticket createTicket(TicketDTO ticketDTO) throws Exception {
        User existingUser = userRepository.findById(ticketDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        TicketCategory ticketCategory = ticketCategoryRepository.findById(ticketDTO.getTicketCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Ticket Category not found"));

        //Check remaining tickets then decrement
        int remainingTickets = ticketRepository.getRemainingTicketsByTicketCategoryId(ticketDTO.getTicketCategoryId());
        if (remainingTickets <= 0) {
            throw new Exception("No remaining tickets for this category. Please try another category");
        }
        ticketCategory.setRemainingCount(remainingTickets - 1);
        ticketCategoryRepository.save(ticketCategory);

        Ticket ticket = Ticket.builder()
                .ticketCategory(ticketCategory)
                .status(ticketDTO.getStatus())
                .user(existingUser)
                .build();
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket updateTicket(Long ticketId, TicketDTO ticketDTO) throws DataNotFoundException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new DataNotFoundException("Ticket not found"));

        User existingUser = userRepository.findById(ticketDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        TicketCategory ticketCategory = ticketCategoryRepository.findById(ticketDTO.getTicketCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Ticket Category not found"));

        ticket.setTicketCategory(ticketCategory);
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setUser(existingUser);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public int getRemainingTicketsByCategoryId(Long ticketCategoryId) {
        return ticketRepository.getRemainingTicketsByTicketCategoryId(ticketCategoryId);
    }
}
