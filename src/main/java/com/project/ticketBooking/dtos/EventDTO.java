package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
    @NotBlank(message = "Event's name can't be empty")
    @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters")
    private String name;

    @Size(max = 400, message = "Description must be less than 400 characters")
    private String description;

    @NotBlank(message = "Event's location can't be empty")
    private String location;

    @JsonProperty("organization_id")
    @Min(value = 1, message = "Organization's ID must be over 0")
    private Long organizeId;
}
