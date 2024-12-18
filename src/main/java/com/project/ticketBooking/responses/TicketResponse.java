package com.project.ticketBooking.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ticketBooking.models.Ticket;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {
    private Long id;
    @JsonProperty("ticket_category_id")
    private Long ticketCategoryId;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("user_email")
    private String userEmail;
    private String status;
    @JsonProperty("ticket_category_name")
    private String ticketCategoryName;
    @JsonProperty("ticket_order_detail_id")
    private Long ticketOrderDetailId;
    @JsonProperty("price")
    private Integer ticketCategoryPrice;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public static TicketResponse fromTicket(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .ticketCategoryId(ticket.getTicketCategory().getId())
                .userId(ticket.getUser().getId())
                .userName(ticket.getUser().getFullName())
                .userEmail(ticket.getUser().getEmail())
                .status(ticket.getStatus())
                .ticketCategoryName(ticket.getTicketCategory().getCategoryName())
                .ticketOrderDetailId(ticket.getTicketOrderDetail().getId())
                .ticketCategoryPrice(ticket.getTicketCategory().getPrice())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }

}
