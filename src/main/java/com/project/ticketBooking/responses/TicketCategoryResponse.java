package com.project.ticketBooking.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ticketBooking.models.TicketCategory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketCategoryResponse {
    private Long id;
    @JsonProperty("category_name")
    private String categoryName;
    private Integer price;

    @JsonProperty("remaining_count")
    private Integer remainingCount;

    public static TicketCategoryResponse fromTicketCategory(TicketCategory ticketCategory) {
        return TicketCategoryResponse.builder()
                .id(ticketCategory.getId())
                .categoryName(ticketCategory.getCategoryName())
                .price(ticketCategory.getPrice())
                .remainingCount(ticketCategory.getRemainingCount())
                .build();
    }
}
