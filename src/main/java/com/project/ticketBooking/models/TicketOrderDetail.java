package com.project.ticketBooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "ticket_order_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketOrderDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticket_order_id")
    private TicketOrder ticketOrder;

    @ManyToOne
    @JoinColumn(name = "ticket_category_id")
    private TicketCategory ticketCategory;

    @Column(name = "number_of_tickets", columnDefinition = "int default null")
    @Min(value = 1, message = "Number of tickets must be greater than 0")
    private Integer numberOfTickets;

    @Column(name = "total_money", columnDefinition = "float default null")
    @Min(value = 0, message = "Total money must be greater than or equal to 0")
    private Float totalMoney;
}