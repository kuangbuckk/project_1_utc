package com.project.ticketBooking.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventListResponse {
    private List<EventResponse> events;
    private int totalPages;
}
