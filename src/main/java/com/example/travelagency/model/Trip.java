package com.example.travelagency.model;

import javax.persistence.*;
import javax.validation.constraints.Future;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    private LocalDate departureDate;

    private LocalDate returnDate;

    @OneToOne
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY)
    Guide guide;

    @ManyToMany(mappedBy = "trips")
    private List<AppUser> appUsers = new ArrayList<>();
}








