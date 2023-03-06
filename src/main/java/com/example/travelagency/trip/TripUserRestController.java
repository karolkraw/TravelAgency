package com.example.travelagency.trip;

import com.example.travelagency.trip.dto.TripReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.travelagency.trip.dto.TripDtoMapper.mapTripToTripReadDto;
import static com.example.travelagency.trip.dto.TripDtoMapper.mapTripsToTripReadDtos;

@RestController
@RequestMapping("/user/trips")
@RequiredArgsConstructor
public class TripUserRestController {
    private final TripService tripService;

    @GetMapping("/{id}")
    public ResponseEntity<TripReadDto> getTrip(@PathVariable Long id) {
        TripReadDto trip = mapTripToTripReadDto(tripService.getTrip(id));
        return ResponseEntity.ok(trip);
    }

    @GetMapping
    public ResponseEntity<List<TripReadDto>> getAllTrips(@RequestParam(required = false) Integer page) {
        if (page == null || page < 0) page = 0;
        List<TripReadDto> trips = mapTripsToTripReadDtos(tripService.getAllTrips(page));
        return ResponseEntity.ok(trips);
    }
}
