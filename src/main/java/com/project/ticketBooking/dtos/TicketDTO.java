package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    @NotBlank(message = "Ticket's name can't be empty")
    @Min(value = 1, message = "Ticket's name must be at least 1 character")
    @Max(value = 255, message = "Ticket's name maximum is 255 characters")
    private String name;

    @NotEmpty(message = "Ticket's price can't be empty")
    @Min(value = 1, message = "Ticket's price must be greater than 0")
    private Integer price;

    private String description;

    @JsonProperty(value = "event_id")
    @Min(value = 1, message = "Event's ID must be over 0")
    private Long eventId;
}
