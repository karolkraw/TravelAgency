package com.example.travelagency.controller.dto.trip;

import com.example.travelagency.controller.dto.destination.DestinationDto;
import com.example.travelagency.controller.dto.guide.GuideReadDto;
import com.example.travelagency.controller.dto.user.AppUserReadDto;
import javax.validation.constraints.Future;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private Long id;

    private BigDecimal price;

    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate departureDate;

    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;

    private DestinationDto destinationDto;

    private GuideReadDto guideReadDto;

    private List<AppUserReadDto> appUserReadDtos = new ArrayList<>();
}
