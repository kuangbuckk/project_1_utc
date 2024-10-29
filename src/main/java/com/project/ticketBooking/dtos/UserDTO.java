package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @NotNull(message = "Password cannot be null")
    @JsonProperty("password")
    private String password;

    @JsonProperty("is_active")
    private Short isActive;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("facebook_account_id")
    private Integer facebookAccountId;

    @JsonProperty("google_account_id")
    private Integer googleAccountId;

    @NotNull(message = "Role ID cannot be null")
    @JsonProperty("role_id")
    @Min(value = 1, message = "Role ID must be greater than 0")
    private Integer roleId;
}