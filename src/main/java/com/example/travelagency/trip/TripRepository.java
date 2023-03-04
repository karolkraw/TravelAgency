package com.example.travelagency.trip;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    /*@Query("SELECT t FROM Trip t " +
            "LEFT JOIN FETCH t.destination d " +
            "LEFT JOIN FETCH t.guide g " +
            "LEFT JOIN FETCH t.appUsers au")*/
    @Query("Select t from Trip t")
    List<Trip> findAllTrips(Pageable page);
}
