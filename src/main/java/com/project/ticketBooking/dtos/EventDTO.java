package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    @NotBlank(message = "Event's name can't be empty")
    @Min(value = 3, message = "Event's name must be over 3 characters")
    @Max(value = 100, message = "Event's name must be less than 100 characters")
    private String name;

    @Max(value = 400, message = "Event's description must be less than 400 characters")
    private String description;

    @NotBlank(message = "Event's location can't be empty")
    private String location;

    @JsonProperty("organization_id")
    @Min(value = 1, message = "Organization's ID must be over 0")
    private Long organizeId;

}
