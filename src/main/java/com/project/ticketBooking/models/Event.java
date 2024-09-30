package com.project.ticketBooking.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location", nullable = false)
    private String location;

    //organization
    @ManyToOne
    @Column(name = "organization_id", nullable = false)
    private Organization organizationId;
}
