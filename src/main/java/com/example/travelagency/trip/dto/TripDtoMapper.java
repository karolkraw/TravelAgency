package com.example.travelagency.trip.dto;

import com.example.travelagency.trip.Trip;

import java.util.ArrayList;
import java.util.List;

import static com.example.travelagency.user.dto.AppUserDtoMapper.*;
import static com.example.travelagency.destination.dto.DestinationDtoMapper.*;
import static com.example.travelagency.guide.dto.GuideDtoMapper.mapGuideReadDtoToGuide;
import static com.example.travelagency.guide.dto.GuideDtoMapper.mapGuideToGuideReadDto;

public class TripDtoMapper {
    private TripDtoMapper() {}

    public static TripDto mapTripToDto(Trip trip) {
        if (trip == null) return null;
        return TripDto.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destination(mapDestinationToDto(trip.getDestination()))
                .guide(mapGuideToGuideReadDto(trip.getGuide()))
                .users(mapAppUsersToAppUserReadDtos(trip.getAppUsers()))
                .build();
    }

    public static List<TripDto> mapTripsToDtos(List<Trip> trips) {
        if (trips == null) return null;
        return trips.stream()
                .map(TripDtoMapper::mapTripToDto)
                .toList();
    }

    public static Trip mapDtoToTrip(Long id, TripDto tripDto) {
        if (tripDto == null) return null;
        return Trip.builder().id(id).price(tripDto.getPrice())
                .departureDate(tripDto.getDepartureDate()).returnDate(tripDto.getReturnDate())
                .destination(mapDtoToDestination(tripDto.getDestination().getId(), tripDto.getDestination()))
                .guide(mapGuideReadDtoToGuide(tripDto.getGuide().getId(), tripDto.getGuide()))
                .appUsers(mapAppUserReadDtosToAppUsers(tripDto.getUsers()))
                .build();
    }

    public static Trip mapRequestDtoToTrip(Long id, TripRequestDto tripDto) {
        if (tripDto == null) return null;
        return Trip.builder().id(id).price(tripDto.getPrice())
                .departureDate(tripDto.getDepartureDate()).returnDate(tripDto.getReturnDate())
                .destination(mapDtoToDestination(tripDto.getDestination().getId(), tripDto.getDestination()))
                .guide(mapGuideReadDtoToGuide(tripDto.getGuide().getId(), tripDto.getGuide()))
                .appUsers(new ArrayList<>())
                .build();
    }

    public static TripReadDto mapTripToTripReadDto(Trip trip) {
        if (trip == null) return null;
        return TripReadDto.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destination(mapDestinationToDto(trip.getDestination()))
                .build();
    }

    public static Trip mapTripReadDtoToTrip(TripReadDto trip) {
        if (trip == null) return null;
        return Trip.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destination(mapDtoToDestination(trip.getDestination().getId(), trip.getDestination()))
                .build();
    }

    public static List<TripReadDto> mapTripsToTripReadDtos(List<Trip> trips) {
        if (trips == null) return null;
        return trips.stream()
                .map(TripDtoMapper::mapTripToTripReadDto)
                .toList();
    }

    public static List<Trip> mapReadDtoTripsToTrips(List<TripReadDto> trips) {
        if (trips == null) return null;
        return trips.stream()
                .map(TripDtoMapper::mapTripReadDtoToTrip)
                .toList();
    }
}
