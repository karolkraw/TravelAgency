package com.example.travelagency.user.dto;

import com.example.travelagency.trip.dto.TripDtoMapper;
import com.example.travelagency.user.AppUser;

import java.util.List;

public class AppUserDtoMapper {
    private AppUserDtoMapper() {}

    public static AppUserDto mapAppUserToDto(AppUser appUser) {
        if(appUser == null) return null;
        return AppUserDto.builder().id(appUser.getId()).firstName(appUser.getFirstName()).lastName(appUser.getLastName())
                .passportNumber(appUser.getPassportNumber()).email(appUser.getEmail()).appUserRole(appUser.getAppUserRole())
                .locked(appUser.getLocked()).enabled(appUser.getEnabled())
                .trips(TripDtoMapper.mapTripsToTripReadDtos(appUser.getTrips())).build();
    }

    public static AppUser mapDtoToAppUser(Long id, AppUserDto appUser) {
        if(appUser == null) return null;
        return AppUser.builder().id(id).firstName(appUser.getFirstName()).lastName(appUser.getLastName())
                .passportNumber(appUser.getPassportNumber()).email(appUser.getEmail()).appUserRole(appUser.getAppUserRole())
                .locked(appUser.getLocked()).enabled(appUser.getEnabled())
                .trips(TripDtoMapper.mapReadDtoTripsToTrips(appUser.getTrips())).build();
    }

    public static List<AppUserReadDto> mapAppUsersToAppUserReadDtos(List<AppUser> users) {
        if (users == null) return null;
        return users.stream()
                .map(AppUserDtoMapper::mapAppUserToAppUserReadDto)
                .toList();
    }

    public static AppUserReadDto mapAppUserToAppUserReadDto(AppUser appUser) {
        if (appUser == null) return null;
        return AppUserReadDto.builder().id(appUser.getId()).firstName(appUser.getFirstName()).lastName(appUser.getLastName())
                .passportNumber(appUser.getPassportNumber()).email(appUser.getEmail()).appUserRole(appUser.getAppUserRole())
                .locked(appUser.getLocked()).enabled(appUser.getEnabled())
                .build();
    }




    public static List<AppUserDto> mapAppUsersToDtos(List<AppUser> appUsers) {
        if (appUsers == null) return null;
        return appUsers.stream()
                .map(AppUserDtoMapper::mapAppUserToDto)
                .toList();
    }

    public static List<AppUser> mapAppUserReadDtosToAppUsers(List<AppUserReadDto> appUsers) {
        if (appUsers == null) return null;
        return appUsers.stream()
                .map(AppUserDtoMapper::mapAppUserReadDtoToAppUser)
                .toList();
    }

    public static AppUser mapAppUserReadDtoToAppUser(AppUserReadDto appUser) {
        if (appUser == null) return null;
        return AppUser.builder().id(appUser.getId()).firstName(appUser.getFirstName()).lastName(appUser.getLastName())
                .passportNumber(appUser.getPassportNumber()).email(appUser.getEmail()).appUserRole(appUser.getAppUserRole())
                .locked(appUser.getLocked()).enabled(appUser.getEnabled()).trips((List.of()))
                .build();
    }


    /*public static AppUser mapDtoToAppUser(Long id, AppUserDto AppUserDto) {

    }*/
}
