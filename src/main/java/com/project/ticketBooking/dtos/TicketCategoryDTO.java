package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketCategoryDTO {
    @NotBlank(message = "Category name cannot be null")
    @JsonProperty("category_name")
    private String categoryName;

    @NotNull(message = "Price cannot be null")
    private Integer price;

    @NotNull(message = "Remaining count cannot be null")
    @JsonProperty("remaining_count")
    private Integer remainingCount;

    @NotNull(message = "Event ID cannot be null")
    @JsonProperty("event_id")
    @Min(value = 1, message = "Event ID must be greater than 0")
    private Long eventId;
}