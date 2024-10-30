package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @JsonProperty("start_date")
    @NotNull(message = "Start date and time must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    @NotNull(message = "End date and time must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @JsonProperty("category_id")
    @Min(value = 1, message = "Category's ID must be over 0")
    private Long categoryId;

    @JsonProperty("organization_id")
    @Min(value = 1, message = "Organization's ID must be over 0")
    private Long organizeId;
}
