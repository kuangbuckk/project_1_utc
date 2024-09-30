package com.project.ticketBooking.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotNull(message = "Category's name can not be empty")
    private String name;
}
