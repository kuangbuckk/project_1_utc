package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketOrderDetailDTO {

    @JsonProperty("ticket_order_id")
    @Min(value = 1, message = "Ticket order ID must be greater than 0")
    private Integer ticketOrderId;

    @JsonProperty("ticket_category_id")
    @Min(value = 1, message = "Ticket category ID must be greater than 0")
    private Integer ticketCategoryId;

    @NotNull(message = "Number of tickets cannot be null")
    @JsonProperty("number_of_tickets")
    @Min(value = 1, message = "Number of tickets must be greater than 0")
    private Integer numberOfTickets;

    @NotNull(message = "Total money cannot be null")
    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    private Float totalMoney;
}