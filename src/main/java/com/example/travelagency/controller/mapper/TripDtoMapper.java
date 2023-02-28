package com.example.travelagency.controller.mapper;

import com.example.travelagency.controller.dto.trip.TripDto;
import com.example.travelagency.controller.dto.trip.TripReadDto;
import com.example.travelagency.controller.dto.user.AppUserDto;
import com.example.travelagency.model.AppUser;
import com.example.travelagency.model.Trip;

import java.util.List;

import static com.example.travelagency.controller.mapper.AppUserDtoMapper.*;
import static com.example.travelagency.controller.mapper.DestinationDtoMapper.*;
import static com.example.travelagency.controller.mapper.GuideDtoMapper.mapGuideReadDtoToGuide;
import static com.example.travelagency.controller.mapper.GuideDtoMapper.mapGuideToGuideReadDto;

public class TripDtoMapper {
    private TripDtoMapper() {
    }

    public static TripDto mapTripToDto(Trip trip) {
        if (trip == null) return null;
        return TripDto.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destinationDto(mapDestinationToDto(trip.getDestination()))
                .guideReadDto(mapGuideToGuideReadDto(trip.getGuide()))
                .appUserReadDtos(mapAppUsersToAppUserReadDtos(trip.getAppUsers()))
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
                .destination(mapDtoToDestination(tripDto.getDestinationDto().getId(), tripDto.getDestinationDto()))
                .guide(mapGuideReadDtoToGuide(tripDto.getGuideReadDto().getId(), tripDto.getGuideReadDto()))
                .appUsers(mapAppUserReadDtosToAppUsers(tripDto.getAppUserReadDtos()))
                .build();
    }

    public static TripReadDto mapTripToTripReadDto(Trip trip) {
        if (trip == null) return null;
        return TripReadDto.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destinationDto(mapDestinationToDto(trip.getDestination()))
                .build();
    }

    public static Trip mapTripReadDtoToTrip(TripReadDto trip) {
        if (trip == null) return null;
        return Trip.builder().id(trip.getId()).price(trip.getPrice())
                .departureDate(trip.getDepartureDate()).returnDate(trip.getReturnDate())
                .destination(mapDtoToDestination(trip.getDestinationDto().getId(), trip.getDestinationDto()))
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
