package com.project.ticketBooking.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ticketBooking.models.TicketOrderDetail;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketOrderDetailResponse {
    private Long id;
    @JsonProperty("ticket_order_id")
    private Long ticketOrderId;
    @JsonProperty("ticket_category_id")
    private Long ticketCategoryId;
    @JsonProperty("number_of_tickets")
    private Integer numberOfTickets;
    @JsonProperty("total_money")
    private Float totalMoney;

    public static TicketOrderDetailResponse fromTicketOrderDetail(TicketOrderDetail ticketOrderDetail) {
        return TicketOrderDetailResponse.builder()
                .id(ticketOrderDetail.getId())
                .ticketOrderId(ticketOrderDetail.getTicketOrder().getId())
                .ticketCategoryId(ticketOrderDetail.getTicketCategory().getId())
                .numberOfTickets(ticketOrderDetail.getNumberOfTickets())
                .totalMoney(ticketOrderDetail.getTotalMoney())
                .build();
    }

}
