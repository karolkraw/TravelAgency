package com.example.travelagency.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class GuideDto {
        Long id;

        @NotBlank(message = "last name is required")
        @Size(min = 1, max = 50, message = "last name must be between 1 and 50 characters")
        String firstName;

        @NotBlank(message = "last name is required")
        @Size(min = 1, max = 50, message = "last name must be between 1 and 50 characters")
        String lastName;

        private List<TripDto> trips = new ArrayList<>();

}
