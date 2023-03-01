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

    @OneToOne(fetch = FetchType.LAZY)
    private Destination destination;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    Guide guide;

    @ManyToMany(mappedBy = "trips")
    private List<AppUser> appUsers = new ArrayList<>();

    public void removeUser(AppUser user) {
        appUsers.remove(user);
        user.getTrips().remove(this);
    }


}








