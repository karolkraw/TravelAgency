package com.example.travelagency.guide;

import javax.persistence.*;

import com.example.travelagency.trip.Trip;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "guide")
    private List<Trip> trips = new ArrayList<>();
}
