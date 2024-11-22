package com.project.ticketBooking.services.interfaces;

import com.project.ticketBooking.dtos.TicketOrderDetailDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.TicketOrderDetail;

import java.util.List;

public interface ITicketOrderDetailService {
    TicketOrderDetail getTicketOrderDetailById(Long id) throws DataNotFoundException;
    TicketOrderDetail createTicketOrderDetail(TicketOrderDetailDTO ticketOrderDetailDTO) throws DataNotFoundException;
    TicketOrderDetail updateTicketOrderDetail(Long id, TicketOrderDetailDTO ticketOrderDetailDTO) throws DataNotFoundException;
    void deleteTicketOrderDetail(Long id);
    List<TicketOrderDetail> getTicketOrderDetailsByTicketOrderId(Long ticketOrderId);
    List<TicketOrderDetail> getTicketOrderDetailsByUserId(Long userId) throws DataNotFoundException;
}
