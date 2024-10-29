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
public class UserAvatarDTO {

    @NotNull(message = "Image URL cannot be null")
    @JsonProperty("image_url")
    private String imageUrl;

    @NotNull(message = "User ID cannot be null")
    @JsonProperty("user_id")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Integer userId;
}