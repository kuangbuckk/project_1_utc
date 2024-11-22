package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.TicketDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.*;
import com.project.ticketBooking.repositories.*;
import com.project.ticketBooking.services.interfaces.ITicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService implements ITicketService {
    private final TicketRepository ticketRepository;
    private final TicketCategoryRepository ticketCategoryRepository;
    private final UserRepository userRepository;
    private final TicketOrderDetailRepository ticketOrderDetailRepository;

    @Override
    public List<Ticket> getTicketsByTicketCategoryId(Long ticketCategoryId) {
        return ticketRepository.findAllByTicketCategoryId(ticketCategoryId);
    }


    @Override
    public Ticket getTicketById(Long id) throws DataNotFoundException {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket not found"));
    }

    @Override
    @Transactional
    public Ticket createTicket(TicketDTO ticketDTO) throws Exception {
        User existingUser = userRepository.findById(ticketDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        TicketCategory ticketCategory = ticketCategoryRepository.findById(ticketDTO.getTicketCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Ticket Category not found"));
        TicketOrderDetail ticketOrderDetail = ticketOrderDetailRepository.findById(ticketDTO.getTicketOrderDetailId())
                .orElseThrow(() -> new DataNotFoundException("Ticket Order Detail not found"));

        //Check remaining tickets then decrement
        int remainingTickets = ticketRepository.getRemainingTicketsByTicketCategoryId(ticketDTO.getTicketCategoryId());
        if (remainingTickets <= 0) {
            throw new Exception("No remaining tickets for this category. Please try another category");
        } else {
            int ticketBought = ticketCategory.getRemainingCount() - 1;
            ticketCategory.setRemainingCount(ticketBought);
            ticketCategoryRepository.save(ticketCategory);
        }

        Ticket ticket = Ticket.builder()
                .ticketCategory(ticketCategory)
                .ticketOrderDetail(ticketOrderDetail)
                .status(TicketStatus.PENDING)
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
    public Ticket updateTicketStatus(Long ticketId, String status) throws DataNotFoundException {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new DataNotFoundException("Ticket not found with ID: " + ticketId));
        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }

    @Override
    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    @Override
    public List<Ticket> getTicketByUserId(Long userId) throws DataNotFoundException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return ticketRepository.findAllByUserId(userId);
    }

    @Override
    public List<Ticket> getTicketsByTicketOrderDetailId(Long ticketOrderDetailId) throws DataNotFoundException {
        return ticketRepository.findByTicketOrderDetailId(ticketOrderDetailId);
    }


    @Override
    public int getRemainingTicketsByCategoryId(Long ticketCategoryId) {
        return ticketRepository.getRemainingTicketsByTicketCategoryId(ticketCategoryId);
    }
}
