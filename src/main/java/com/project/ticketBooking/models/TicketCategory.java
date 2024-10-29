package com.project.ticketBooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "ticket_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketCategory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Category name cannot be null")
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @Column(name = "price", nullable = false)
    private Integer price;

    @NotNull(message = "Remaining count cannot be null")
    @Min(value = 0, message = "Remaining count must be greater than or equal to 0")
    @Column(name = "remaining_count", nullable = false)
    private Integer remainingCount;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @Min(value = 1, message = "Event ID must be greater than 0")
    private Event event;
}