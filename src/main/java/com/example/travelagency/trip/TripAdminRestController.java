package com.example.travelagency.trip;

import com.example.travelagency.trip.dto.TripDto;
import com.example.travelagency.trip.dto.TripRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.travelagency.trip.dto.TripDtoMapper.*;

@RestController
@RequestMapping("/admin/trips")
@RequiredArgsConstructor
public class TripAdminRestController {
    private static final Long EMPTY_ID = 0L;
    private final TripService tripService;

    @GetMapping("/{id}")
    public ResponseEntity<TripDto> getTrip(@PathVariable Long id) {
        TripDto tripDto = mapTripToDto(tripService.getTrip(id));
        return ResponseEntity.ok(tripDto);
    }

    @GetMapping
    public ResponseEntity<List<TripDto>> getAllTrips(@RequestParam(required = false) Integer page) {
        if (page == null || page < 0) page = 0;
        List<TripDto> trips = mapTripsToDtos(tripService.getAllTripsWithUsers(page));
        return ResponseEntity.ok(trips);
    }

    @PostMapping
    public ResponseEntity<TripDto> addTrip(@RequestBody @Valid TripRequestDto tripDto) {
        Trip trip = tripService.addTrip(mapRequestDtoToTrip(EMPTY_ID, tripDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapTripToDto(trip));
    }

    @PostMapping("/{tripId}/users/{userId}")
    public ResponseEntity<TripDto> addUserToTrip(@PathVariable Long tripId, @PathVariable Long userId) {
        Trip trip = tripService.addUserToTrip(tripId, userId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapTripToDto(trip));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTrip(@PathVariable Long id, @RequestBody @Valid TripRequestDto tripDto) {
        tripService.updateTrip(mapRequestDtoToTrip(id, tripDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }
}
