package com.project.ticketBooking.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ticketBooking.models.Event;
import com.project.ticketBooking.models.EventImage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventResponse {
    private Long id;
    private String name;
    private String description;
    private String location;
    private String thumbnail;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    @JsonProperty("end_date")
    private LocalDateTime endDate;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("organization_id")
    private Long organizeId;

    @JsonProperty("organization_name")
    private String organizationName;

    @JsonProperty("event_images")
    private List<EventImage> eventImages = new ArrayList<>();

    public static EventResponse fromEvent(Event event) {
        EventResponse eventResponse = EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .description(event.getDescription())
                .thumbnail(event.getThumbnail())
                .location(event.getLocation())
                .categoryId(event.getCategory().getId())
                .categoryName(event.getCategory().getName())
                .organizeId(event.getOrganization().getId())
                .organizationName(event.getOrganization().getName())
                .eventImages(event.getEventImages())
                .build();
        eventResponse.setStartDate(event.getStartDate());
        eventResponse.setEndDate(event.getEndDate());
        return eventResponse;
    }
}
