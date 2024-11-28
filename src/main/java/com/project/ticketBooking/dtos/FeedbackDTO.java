package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDTO {
    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Type cannot be null")
    private String type;

    @NotBlank(message = "Content cannot be null")
    @Length(min = 10, message = "Content must be at least 10 characters long")
    private String content;

    @NotNull(message = "User id cannot be null")
    @JsonProperty("user_id")
    private Long userId;
}
