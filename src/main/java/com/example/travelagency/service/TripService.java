package com.example.travelagency.service;

import com.example.travelagency.controller.dto.trip.TripDto;
import com.example.travelagency.model.Trip;
import com.example.travelagency.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepository TripRepository;

    public Trip addTrip(Trip Trip) {
        return TripRepository.save(Trip);
    }

    public Trip getTrip(Long id) {
        return TripRepository.findById(id).orElseThrow();
    }

    public List<Trip> getAllTrips() {
        return TripRepository.findAll();
    }

    public void updateTrip(Trip trip) {
    }

    public void deleteTrip(Long id) {
    }
}
