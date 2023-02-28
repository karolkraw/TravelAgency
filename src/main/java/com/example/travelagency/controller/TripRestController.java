package com.example.travelagency.controller;

import com.example.travelagency.controller.dto.trip.TripDto;
import com.example.travelagency.model.Trip;
import com.example.travelagency.service.TripService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.example.travelagency.controller.mapper.TripDtoMapper.*;

@RestController
@RequestMapping("trips")
@RequiredArgsConstructor
public class TripRestController {
    private static final Long EMPTY_ID = null;
    @Value("${app.url}")
    private String appUrl;
    private final TripService tripService;

    @GetMapping("/get/{id}")
    public ResponseEntity<TripDto> getTrip(@PathVariable Long id) {
        TripDto TripDto = mapTripToDto(tripService.getTrip(id));
        return ResponseEntity.ok(TripDto);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TripDto>> getAllTrips() {
        List<TripDto> Trips = mapTripsToDtos(tripService.getAllTrips());
        return ResponseEntity.ok(Trips);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addTrip(@RequestBody @Valid TripDto tripDto) {
        Trip trip = tripService.addTrip(mapDtoToTrip(EMPTY_ID, tripDto));
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(appUrl + "/trips/get/{id}")
                .buildAndExpand(trip.getId());
        return ResponseEntity.created(uriComponents.toUri())
                .build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateTrip(@PathVariable Long id, @RequestBody @Valid TripDto tripDto) {
        tripService.updateTrip(mapDtoToTrip(id, tripDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
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
