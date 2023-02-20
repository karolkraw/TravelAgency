package com.example.travelagency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    @Future
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate departureDate;

    @Future
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;

    @OneToOne
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    TourGuide tourGuide;

    @ManyToMany(mappedBy = "trips")
    private Set<Client> clients = new HashSet<>();

    public void addClient(Client client) {
        clients.add(client);
        client.getTrips().add(this);
    }
}








