package com.example.travelagency.destination.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DestinationDto {
    Long id;

    @NotBlank(message = "destination is required")
    @Size(min = 1, max = 50, message = "destination must be between 1 and 50 characters")
    public String destination;
}
