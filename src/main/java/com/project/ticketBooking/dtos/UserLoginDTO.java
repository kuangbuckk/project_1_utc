package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoginDTO {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password can't be blank")
    private String password;

//    @Min(value = 1, message = "You must enter role's Id")
//    @JsonProperty("role_id")
//    private Long roleId;
}
