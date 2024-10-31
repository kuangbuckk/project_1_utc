// src/main/java/com/project/ticketBooking/services/TicketOrderService.java
package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.TicketOrderDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.TicketOrder;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.repositories.TicketOrderRepository;
import com.project.ticketBooking.repositories.UserRepository;
import com.project.ticketBooking.services.interfaces.ITicketOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketOrderService implements ITicketOrderService {
    private final TicketOrderRepository ticketOrderRepository;
    private final UserRepository userRepository;

    @Override
    public TicketOrder getTicketOrderById(Long id) throws DataNotFoundException {
        return ticketOrderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket Order not found"));
    }

    @Override
    @Transactional
    public TicketOrder createTicketOrder(TicketOrderDTO ticketOrderDTO) throws DataNotFoundException {
        User existingUser = userRepository.findById(ticketOrderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        TicketOrder ticketOrder = TicketOrder.builder()
                .user(existingUser)
                .orderDate(ticketOrderDTO.getOrderDate())
                .totalMoney(ticketOrderDTO.getTotalMoney())
                .paymentMethod(ticketOrderDTO.getPaymentMethod())
                .paymentStatus(ticketOrderDTO.getPaymentStatus())
                .build();
        return ticketOrderRepository.save(ticketOrder);
    }

    @Override
    @Transactional
    public TicketOrder updateTicketOrder(Long id, TicketOrderDTO ticketOrderDTO) throws DataNotFoundException {
        TicketOrder ticketOrder = ticketOrderRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket Order not found"));

        User existingUser = userRepository.findById(ticketOrderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        ticketOrder.setUser(existingUser);
        ticketOrder.setOrderDate(ticketOrderDTO.getOrderDate());
        ticketOrder.setTotalMoney(ticketOrderDTO.getTotalMoney());
        ticketOrder.setPaymentMethod(ticketOrderDTO.getPaymentMethod());
        ticketOrder.setPaymentStatus(ticketOrderDTO.getPaymentStatus());
        return ticketOrderRepository.save(ticketOrder);
    }

    @Override
    @Transactional
    public void deleteTicketOrder(Long id) {
        ticketOrderRepository.deleteById(id);
    }

    @Override
    public List<TicketOrder> getTicketOrdersByUserId(Long userId) {
        return ticketOrderRepository.findByUserId(userId);
    }
}