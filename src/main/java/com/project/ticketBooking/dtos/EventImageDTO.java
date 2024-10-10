package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventImageDTO {
    @NotBlank(message = "Event image can't be empty")
    @JsonProperty("image_url")
    private String imageUrl;

    @NotBlank(message = "Event image must belong to an event")
    @JsonProperty("event_id")
    @Min(value = 1, message = "Event ID must be greater than 0")
    private Long eventId;
}
