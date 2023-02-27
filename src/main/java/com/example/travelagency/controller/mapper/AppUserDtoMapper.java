package com.example.travelagency.controller.mapper;

import com.example.travelagency.controller.dto.user.AppUserDto;
import com.example.travelagency.model.AppUser;
import com.example.travelagency.model.AppUserRole;
import com.example.travelagency.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class AppUserDtoMapper {
    private AppUserDtoMapper() {}

    public static AppUserDto mapAppUserToDto(AppUser appUser) {
        return AppUserDto.builder().id(appUser.getId()).firstName(appUser.getFirstName()).lastName(appUser.getLastName())
                .passportNumber(appUser.getPassportNumber()).email(appUser.getEmail()).appUserRole(appUser.getAppUserRole())
                .locked(appUser.getLocked()).enabled(appUser.getEnabled())
                .tripsDto(TripDtoMapper.mapTripsToDtos(appUser.getTrips())).build();
    }


    String email;

    private AppUserRole appUserRole;

    private Boolean locked;
    private Boolean enabled;

    private List<Trip> trips = new ArrayList<>();

    public static List<AppUserDto> mapAppUsersToDtos(List<AppUser> AppUsers) {
        return AppUsers.stream()
                .map(AppUserDtoMapper::mapAppUserToDto)
                .toList();
    }

    /*public static AppUser mapDtoToAppUser(Long id, AppUserDto AppUserDto) {

    }*/
}
