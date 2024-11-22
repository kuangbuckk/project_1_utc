package com.project.ticketBooking.services;

import com.project.ticketBooking.dtos.TicketOrderDetailDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.TicketCategory;
import com.project.ticketBooking.models.TicketOrder;
import com.project.ticketBooking.models.TicketOrderDetail;
import com.project.ticketBooking.models.User;
import com.project.ticketBooking.repositories.TicketCategoryRepository;
import com.project.ticketBooking.repositories.TicketOrderDetailRepository;
import com.project.ticketBooking.repositories.TicketOrderRepository;
import com.project.ticketBooking.repositories.UserRepository;
import com.project.ticketBooking.services.interfaces.ITicketOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketOrderDetailService implements ITicketOrderDetailService {
    private final TicketOrderDetailRepository ticketOrderDetailRepository;
    private final TicketOrderRepository ticketOrderRepository;
    private final TicketCategoryRepository ticketCategoryRepository;
    private final UserRepository userRepository;

    @Override
    public TicketOrderDetail getTicketOrderDetailById(Long id) throws DataNotFoundException {
        return ticketOrderDetailRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Ticket Order Detail not found"));
    }

    @Override
public TicketOrderDetail createTicketOrderDetail(TicketOrderDetailDTO ticketOrderDetailDTO) throws DataNotFoundException {
    TicketOrder existingTicketOrder = ticketOrderRepository.findById(ticketOrderDetailDTO.getTicketOrderId())
            .orElseThrow(() -> new DataNotFoundException("Ticket Order not found"));
    TicketCategory existingTicketCategory = ticketCategoryRepository.findById(ticketOrderDetailDTO.getTicketCategoryId())
            .orElseThrow(() -> new DataNotFoundException("Ticket Category not found"));

    TicketOrderDetail ticketOrderDetail = TicketOrderDetail.builder()
            .ticketOrder(existingTicketOrder)
            .ticketCategory(existingTicketCategory)
            .numberOfTickets(ticketOrderDetailDTO.getNumberOfTickets())
            .totalMoney(ticketOrderDetailDTO.getTotalMoney())
            .build();
    return ticketOrderDetailRepository.save(ticketOrderDetail);
}

@Override
public TicketOrderDetail updateTicketOrderDetail(Long id, TicketOrderDetailDTO ticketOrderDetailDTO) throws DataNotFoundException {
    TicketOrderDetail ticketOrderDetail = ticketOrderDetailRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("Ticket Order Detail not found"));
    TicketOrder existingTicketOrder = ticketOrderRepository.findById(ticketOrderDetailDTO.getTicketOrderId())
            .orElseThrow(() -> new DataNotFoundException("Ticket Order not found"));
    TicketCategory existingTicketCategory = ticketCategoryRepository.findById(ticketOrderDetailDTO.getTicketCategoryId())
            .orElseThrow(() -> new DataNotFoundException("Ticket Category not found"));
    ticketOrderDetail.setTicketOrder(existingTicketOrder);
    ticketOrderDetail.setTicketCategory(existingTicketCategory);
    ticketOrderDetail.setNumberOfTickets(ticketOrderDetailDTO.getNumberOfTickets());
    ticketOrderDetail.setTotalMoney(ticketOrderDetailDTO.getTotalMoney());
    return ticketOrderDetailRepository.save(ticketOrderDetail);
}

    @Override
    public void deleteTicketOrderDetail(Long id) {
        ticketOrderDetailRepository.deleteById(id);
    }

    @Override
    public List<TicketOrderDetail> getTicketOrderDetailsByTicketOrderId(Long ticketOrderId) {
        return ticketOrderDetailRepository.findByTicketOrderId(ticketOrderId);
    }

    @Override
    public List<TicketOrderDetail> getTicketOrderDetailsByUserId(Long userId) throws DataNotFoundException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        List<TicketOrder> ticketOrders = ticketOrderRepository.findByUserId(existingUser.getId());
        List<Long> ticketOrderIds = ticketOrders.stream().map(TicketOrder::getId).collect(Collectors.toList());
        return ticketOrderDetailRepository.findByTicketOrderIdIn(ticketOrderIds);
    }
}
