package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateAdminDTO {
    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("is_active")
    private Short isActive;

    @NotNull(message = "Role ID cannot be null")
    @JsonProperty("role_id")
    @Min(value = 1, message = "Role ID must be greater than 0")
    private Long roleId;

    @JsonProperty("organization_id")
    @NotNull(message = "Organization ID cannot be null")
    @Min(value = 1, message = "Organization ID must be greater than 0")
    private Long organizationId;
}
