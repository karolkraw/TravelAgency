package com.example.travelagency.controller.mapper;

import com.example.travelagency.controller.dto.trip.TripDto;
import com.example.travelagency.controller.dto.trip.TripReadDto;
import com.example.travelagency.model.Trip;

import java.util.List;

import static com.example.travelagency.controller.mapper.DestinationDtoMapper.*;

public class TripDtoMapper {
    private TripDtoMapper() {
    }

    public static TripDto mapTripToDto(Trip trip) {
        return TripDto.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destinationDto(mapDestinationToDto(trip.getDestination()))
                //.guideDto(mapGuideToDto(trip.getGuide()))
                //.appUsersDto(mapAppUsersToDtos(trip.getAppUsers()))
                .build();
    }

    public static List<TripDto> mapTripsToDtos(List<Trip> trips) {
        return trips.stream()
                .map(TripDtoMapper::mapTripToDto)
                .toList();
    }


    public static Trip mapDtoToTrip(TripDto tripDto) {
        /*return Trip.builder().id(id).price(tripDto.getPrice())
                .departureDate(tripDto.getDepartureDate()).returnDate(tripDto.getReturnDate())
                .destination(mapDtoToDestination(tripDto.getDestinationDto().getId(), tripDto.getDestinationDto()))
                .guide(mapDtoToGuide(tripDto.getId(), tripDto.getGuideDto()))
                .appUsersDto(mapDtosToAppUsers(tripDto.getAppUsersDto()))
                .build();
    }*/
        return new Trip();
    }


    public static TripReadDto mapTripToTripReadDto(Trip trip) {
        return TripReadDto.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destinationDto(mapDestinationToDto(trip.getDestination()))
                .build();
    }

    public static Trip mapTripReadDtoToTrip(TripReadDto trip) {
        return Trip.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destination(mapDtoToDestination(trip.getDestinationDto().getId(), trip.getDestinationDto()))
                .build();
    }

    public static List<TripReadDto> mapTripsToTripReadDtos(List<Trip> trips) {
        return trips.stream()
                .map(TripDtoMapper::mapTripToTripReadDto)
                .toList();
    }

    public static List<Trip> mapReadDtoTripsToTrips(List<TripReadDto> trips) {
        return trips.stream()
                .map(TripDtoMapper::mapTripReadDtoToTrip)
                .toList();
    }
}
