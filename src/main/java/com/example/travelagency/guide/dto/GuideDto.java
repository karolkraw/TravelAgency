package com.example.travelagency.guide.dto;

import com.example.travelagency.trip.dto.TripReadDto;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@EqualsAndHashCode
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

        private List<TripReadDto> trips = new ArrayList<>();
}
