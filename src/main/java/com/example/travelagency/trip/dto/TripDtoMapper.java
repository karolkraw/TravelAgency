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
        TripDto.TripDtoBuilder builder = TripDto.builder()
                .id(trip.getId())
                .price(trip.getPrice())
                .departureDate(trip.getDepartureDate())
                .returnDate(trip.getReturnDate());
        if (trip.getDestination() != null) {
            builder.destination(mapDestinationToDto(trip.getDestination()));
        }
        if (trip.getGuide() != null) {
            builder.guide(mapGuideToGuideReadDto(trip.getGuide()));
        }
        builder.users(mapAppUsersToAppUserReadDtos(trip.getAppUsers()));
        return builder.build();
    }

    public static List<TripDto> mapTripsToDtos(List<Trip> trips) {
        if (trips == null) return null;
        return trips.stream()
                .map(TripDtoMapper::mapTripToDto)
                .toList();
    }

    public static Trip mapDtoToTrip(Long id, TripDto tripDto) {
        if (tripDto == null) return null;
        Trip.TripBuilder builder = Trip.builder()
                .id(id)
                .price(tripDto.getPrice())
                .departureDate(tripDto.getDepartureDate())
                .returnDate(tripDto.getReturnDate());
        if (tripDto.getDestination() != null) {
            builder.destination(mapDtoToDestination(tripDto.getDestination().getId(), tripDto.getDestination()));
        }
        if (tripDto.getGuide() != null) {
            builder.guide(mapGuideReadDtoToGuide(tripDto.getGuide().getId(), tripDto.getGuide()));
        }
        builder.appUsers(mapAppUserReadDtosToAppUsers(tripDto.getUsers()));
        return builder.build();
    }

    public static Trip mapRequestDtoToTrip(Long id, TripRequestDto tripDto) {
        if (tripDto == null) return null;
        Trip.TripBuilder builder = Trip.builder().id(id).price(tripDto.getPrice())
                .departureDate(tripDto.getDepartureDate()).returnDate(tripDto.getReturnDate());
        if (tripDto.getDestination() != null) {
            builder = builder.destination(mapDtoToDestination(tripDto.getDestination().getId(), tripDto.getDestination()));
        }
        if (tripDto.getGuide() != null) {
            builder = builder.guide(mapGuideReadDtoToGuide(tripDto.getGuide().getId(), tripDto.getGuide()));
        }
        return builder.appUsers(new ArrayList<>()).build();
    }

    public static TripReadDto mapTripToTripReadDto(Trip trip) {
        if (trip == null) return null;
        TripReadDto.TripReadDtoBuilder builder = TripReadDto.builder()
                .id(trip.getId())
                .price(trip.getPrice())
                .departureDate(trip.getDepartureDate())
                .returnDate(trip.getReturnDate());
        if (trip.getDestination() != null) {
            builder.destination(mapDestinationToDto(trip.getDestination()));
        }
        return builder.build();
    }

    public static Trip mapTripReadDtoToTrip(TripReadDto trip) {
        if (trip == null) return null;
        Trip.TripBuilder builder = Trip.builder()
                .id(trip.getId())
                .price(trip.getPrice())
                .departureDate(trip.getDepartureDate())
                .returnDate(trip.getReturnDate());
        if (trip.getDestination() != null) {
            builder.destination(mapDtoToDestination(trip.getDestination().getId(), trip.getDestination()));
        }
        return builder.build();
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
