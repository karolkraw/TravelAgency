package com.example.travelagency.controller.dto;

import com.example.travelagency.model.AppUser;
import com.example.travelagency.model.Destination;
import com.example.travelagency.model.Guide;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class TripDto {
    private Long id;

    private BigDecimal price;

    @Future
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate departureDate;

    @Future
    //@JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate returnDate;

    @OneToOne
    private Destination destination;

    GuideDto guideDto;

    @ManyToMany(mappedBy = "trips")
    private List<AppUserDto> appUsersDto = new ArrayList<>();
}
