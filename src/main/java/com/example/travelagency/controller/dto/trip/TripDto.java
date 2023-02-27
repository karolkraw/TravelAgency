package com.example.travelagency.controller.dto.trip;

import com.example.travelagency.controller.dto.destination.DestinationDto;
import com.example.travelagency.controller.dto.guide.GuideNestedDto;
import com.example.travelagency.controller.dto.user.AppUserDto;
import com.example.travelagency.controller.dto.user.AppUserNestedDto;
import javax.validation.constraints.Future;
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

    //@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate departureDate;

    //@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;

    private DestinationDto destinationDto;

    private GuideNestedDto guideDto;

    private List<AppUserNestedDto> appUsersDto = new ArrayList<>();
}
