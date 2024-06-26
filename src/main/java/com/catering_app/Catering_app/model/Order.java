package com.catering_app.Catering_app.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    List<OrderedItems> orderedItems = new ArrayList<>();

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    List<OrderedCombos> orderedCombos = new ArrayList<>();

    @ManyToOne
    Events events;

    private LocalDate date;
    private LocalDate orderDate;
    String timeFrom;
    String timeTo;
    private Integer peopleCount;

    private Float totalAmount;
    private Float advanceAmount;
    private boolean isPayFullAmount;
    @Enumerated(EnumType.STRING)
    private Venue venue;

    @OneToOne
    private UserLocation userLocation;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String decorationOption;
    private String transactionId;
    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private Review review;


}
