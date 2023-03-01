package com.example.travelagency.user.dto;

import com.example.travelagency.trip.dto.TripReadDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.travelagency.user.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    private Long id;

    @NotBlank(message = "first name is required")
    @Size(min = 1, max = 50, message = "first name must be between 1 and 50 characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 1, max = 50, message = "last name must be between 1 and 50 characters")
    private String lastName;

    @NotBlank(message = "passport number is required")
    private String passportNumber;

    String email;

    private AppUserRole appUserRole;

    private Boolean locked;
    private Boolean enabled;

    private List<TripReadDto> trips = new ArrayList<>();
}
