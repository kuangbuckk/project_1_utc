package com.project.ticketBooking.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@EntityListeners(com.project.ticketBooking.models.EventListener.class)
public class Event extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    //category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    //organization
    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<EventImage> eventImages;
}
