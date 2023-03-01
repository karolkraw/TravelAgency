package com.example.travelagency.trip;

import javax.validation.Valid;

import com.example.travelagency.trip.dto.TripDto;
import com.example.travelagency.trip.dto.TripReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.example.travelagency.trip.dto.TripDtoMapper.*;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
public class TripRestController {
    private static final Long EMPTY_ID = null;
    @Value("${app.url}")
    private String appUrl;
    private final TripService tripService;

    @GetMapping("/admin/get/{id}")
    public ResponseEntity<TripDto> getTripAdmin(@PathVariable Long id) {
        TripDto tripDto = mapTripToDto(tripService.getTrip(id));
        return ResponseEntity.ok(tripDto);
    }

    @GetMapping("/user/get/{id}")
    public ResponseEntity<TripReadDto> getTripUser(@PathVariable Long id) {
        TripReadDto trip = mapTripToTripReadDto(tripService.getTrip(id));
        return ResponseEntity.ok(trip);
    }

    @GetMapping("/admin/get")
    public ResponseEntity<List<TripDto>> getAllTripsWithUsers(@RequestParam(required = false) Integer page) {
        if (page == null || page < 0) page = 0;
        List<TripDto> trips = mapTripsToDtos(tripService.getAllTripsWithUsers(page));
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/user/get")
    public ResponseEntity<List<TripReadDto>> getAllTrips(@RequestParam(required = false) Integer page) {
        if (page == null || page < 0) page = 0;
        List<TripReadDto> trips = mapTripsToTripReadDtos(tripService.getAllTrips(page));
        return ResponseEntity.ok(trips);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<Object> addTrip(@RequestBody @Valid TripDto tripDto) {
        Trip trip = tripService.addTrip(mapDtoToTrip(EMPTY_ID, tripDto));
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(appUrl + "/trips/admin/get/{id}")
                .buildAndExpand(trip.getId());
        return ResponseEntity.created(uriComponents.toUri())
                .build();
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Object> updateTrip(@PathVariable Long id, @RequestBody @Valid TripDto tripDto) {
        tripService.updateTrip(mapDtoToTrip(id, tripDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Object> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }










    // add trip
    // edit future trip
    // remove trip
    // get all trips to specified destination
    // get all trips that start on specified date
    // get all weekend trips
    // get all more than 2 weeks trips
    // get all past trips
    // get all future trips
    // get all trips from next week
    // get all trips from this year
    // get longest trip
    // get most expensive trip
    // get trip that earned the most money
    // how much money earned specified trip

}
