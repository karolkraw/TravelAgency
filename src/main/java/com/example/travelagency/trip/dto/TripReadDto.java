package com.example.travelagency.trip.dto;

import com.example.travelagency.destination.dto.DestinationDto;
import javax.validation.constraints.Future;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripReadDto {
    private Long id;

    private BigDecimal price;

    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate departureDate;

    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;

    private DestinationDto destination;
}
