package com.example.travelagency.controller.mapper;

import com.example.travelagency.controller.dto.destination.DestinationDto;
import com.example.travelagency.model.Destination;

import java.util.List;

public class DestinationDtoMapper {

    private DestinationDtoMapper() {}

    public static DestinationDto mapDestinationToDto(Destination destination) {
        return new DestinationDto(destination.getId(), destination.getDestination());
    }

    public static Destination mapDtoToDestination(Long id, DestinationDto destinationDto) {
        return new Destination(id, destinationDto.getDestination());

    }

    public static List<DestinationDto> mapDestinationsToDtos(List<Destination> destinations) {
        return destinations.stream().map(DestinationDtoMapper::mapDestinationToDto).toList();

    }

}
