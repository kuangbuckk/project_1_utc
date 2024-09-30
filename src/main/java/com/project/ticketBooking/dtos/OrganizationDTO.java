package com.project.ticketBooking.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {
    @NotBlank(message = "Organization's name can't be empty")
    private String name;
}
