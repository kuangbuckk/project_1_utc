package com.project.ticketBooking.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketOrderDTO {

    @JsonProperty("user_id")
    @Min(value = 1, message = "User ID must be greater than 0")
    private Integer userId;

    @JsonProperty("order_date")
    private LocalDate orderDate;

    @NotNull(message = "Total money cannot be null")
    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    private Float totalMoney;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_status")
    private String paymentStatus;
}