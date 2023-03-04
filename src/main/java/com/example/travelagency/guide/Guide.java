package com.example.travelagency.guide;

import javax.persistence.*;

import com.example.travelagency.trip.Trip;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.MERGE)
    private List<Trip> trips = new ArrayList<>();

    public void removeTrip(Trip trip) {
        trips.remove(trip);
        trip.setGuide(null);
    }
}
