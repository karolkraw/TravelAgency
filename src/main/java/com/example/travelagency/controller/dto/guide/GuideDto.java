package com.example.travelagency.controller.dto.guide;

import com.example.travelagency.controller.dto.trip.TripReadDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GuideDto {
        Long id;

        @NotBlank(message = "last name is required")
        @Size(min = 1, max = 50, message = "last name must be between 1 and 50 characters")
        String firstName;

        @NotBlank(message = "last name is required")
        @Size(min = 1, max = 50, message = "last name must be between 1 and 50 characters")
        String lastName;

        private List<TripReadDto> tripReadDtos = new ArrayList<>();

}
