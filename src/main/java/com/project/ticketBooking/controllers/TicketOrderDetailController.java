// src/main/java/com/project/ticketBooking/controllers/TicketOrderDetailController.java
package com.project.ticketBooking.controllers;

import com.project.ticketBooking.dtos.TicketOrderDetailDTO;
import com.project.ticketBooking.exceptions.DataNotFoundException;
import com.project.ticketBooking.models.TicketOrderDetail;
import com.project.ticketBooking.services.interfaces.ITicketOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/ticketOrderDetails")
@RequiredArgsConstructor
public class TicketOrderDetailController {
    private final ITicketOrderDetailService ticketOrderDetailService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<TicketOrderDetail> getTicketOrderDetailById(@PathVariable Long id) throws DataNotFoundException {
        TicketOrderDetail ticketOrderDetail = ticketOrderDetailService.getTicketOrderDetailById(id);
        return ResponseEntity.ok(ticketOrderDetail);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTicketOrderDetail(
            @Valid @RequestBody TicketOrderDetailDTO ticketOrderDetailDTO,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            TicketOrderDetail newTicketOrderDetail = ticketOrderDetailService.createTicketOrderDetail(ticketOrderDetailDTO);
            return ResponseEntity.ok(newTicketOrderDetail);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateTicketOrderDetail(
            @PathVariable("id") Long ticketOrderDetailId,
            @Valid @RequestBody TicketOrderDetailDTO ticketOrderDetailDTO
    ) {
        try {
            TicketOrderDetail updatedTicketOrderDetail = ticketOrderDetailService.updateTicketOrderDetail(ticketOrderDetailId, ticketOrderDetailDTO);
            return ResponseEntity.ok(updatedTicketOrderDetail);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTicketOrderDetail(
            @PathVariable("id") Long ticketOrderDetailId
    ) {
        try {
            ticketOrderDetailService.deleteTicketOrderDetail(ticketOrderDetailId);
            return ResponseEntity.ok("Deleted ticket order detail with ID: " + ticketOrderDetailId);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getTicketOrderDetailsByOrderId(@PathVariable Long orderId) {
        try {
            List<TicketOrderDetail> ticketOrderDetails = ticketOrderDetailService.getTicketOrderDetailsByTicketOrderId(orderId);
            return ResponseEntity.ok(ticketOrderDetails);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getTicketOrderDetailsByUserId(@PathVariable Long userId) {
        try {
            List<TicketOrderDetail> ticketOrderDetails = ticketOrderDetailService.getTicketOrderDetailsByUserId(userId);
            return ResponseEntity.ok(ticketOrderDetails);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}