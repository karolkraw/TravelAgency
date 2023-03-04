package com.example.travelagency.trip;

import javax.persistence.*;

import com.example.travelagency.destination.Destination;
import com.example.travelagency.guide.Guide;
import com.example.travelagency.user.AppUser;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    Guide guide;

    @ManyToMany(mappedBy = "trips", cascade = CascadeType.MERGE)
    private List<AppUser> appUsers = new ArrayList<>();

    public void removeUser(AppUser user) {
        appUsers.remove(user);
        user.getTrips().remove(this);
    }

    public void addUser(AppUser user) {
        appUsers.add(user);
        user.getTrips().add(this);
    }
}








