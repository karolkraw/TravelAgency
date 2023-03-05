package com.example.travelagency.trip.dto;


import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.guide.dto.GuideReadDto;
import com.example.travelagency.user.dto.AppUserReadDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@EqualsAndHashCode
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

    private DestinationDto destination;

    private GuideReadDto guide;

    private List<AppUserReadDto> users = new ArrayList<>();
}

