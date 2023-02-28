package com.example.travelagency.controller.mapper;

import com.example.travelagency.controller.dto.destination.DestinationDto;
import com.example.travelagency.model.Destination;

import java.util.List;

public class DestinationDtoMapper {

    private DestinationDtoMapper() {}

    public static DestinationDto mapDestinationToDto(Destination destination) {
        if (destination == null) return null;
        return new DestinationDto(destination.getId(), destination.getDestination());
    }

    public static Destination mapDtoToDestination(Long id, DestinationDto destinationDto) {
        if (destinationDto == null) return null;
        return new Destination(id, destinationDto.getDestination());

    }

    public static List<DestinationDto> mapDestinationsToDtos(List<Destination> destinations) {
        if (destinations == null) return null;
        return destinations.stream().map(DestinationDtoMapper::mapDestinationToDto).toList();

    }

}
