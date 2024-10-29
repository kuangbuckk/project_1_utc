package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private String status;

    @NotNull(message = "Ticket category ID cannot be null")
    @JsonProperty("ticket_category_id")
    @Min(value = 1, message = "Ticket category ID must be greater than 0")
    private Long ticketCategoryId;

    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Long userId;
}