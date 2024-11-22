package com.project.ticketBooking.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ticketBooking.models.TicketOrder;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketOrderResponse {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("order_date")
    private LocalDate orderDate;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("payment_status")
    private String paymentStatus;

    @JsonProperty("stripe_token_id")
    private String stripeTokenId;

    @JsonProperty("email")
    private String email;

    public static TicketOrderResponse fromTicketOrder(TicketOrder ticketOrder) {
        return TicketOrderResponse.builder()
                .id(ticketOrder.getId())
                .userId(ticketOrder.getUser().getId())
                .orderDate(ticketOrder.getOrderDate())
                .totalMoney(ticketOrder.getTotalMoney())
                .paymentMethod(ticketOrder.getPaymentMethod())
                .paymentStatus(ticketOrder.getPaymentStatus())
                .stripeTokenId(ticketOrder.getStripeTokenId())
                .email(ticketOrder.getEmail())
                .build();
    }
}
