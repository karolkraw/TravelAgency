package com.example.travelagency.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DestinationDto {
    Long id;

    @NotBlank(message = "destination is required")
    @Size(min = 1, max = 50, message = "destination must be between 1 and 50 characters")
    public String destination;
}
