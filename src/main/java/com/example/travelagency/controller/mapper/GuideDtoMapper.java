package com.example.travelagency.controller.mapper;

import com.example.travelagency.controller.dto.guide.GuideDto;
import com.example.travelagency.model.Guide;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.List;

import static com.example.travelagency.controller.mapper.TripDtoMapper.*;

public class GuideDtoMapper {
    static ModelMapper mapper = new ModelMapper();

    private GuideDtoMapper() {}

    public static GuideDto mapGuideToDto(Guide guide) {
        return new GuideDto(guide.getId(), guide.getFirstName(), guide.getLastName(), mapTripsToTripReadDtos(guide.getTrips()));
    }

    public static Guide mapDtoToGuide(Long id, GuideDto guideDto) {
        return new Guide(id, guideDto.getFirstName(), guideDto.getLastName(), mapReadDtoTripsToTrips(guideDto.getTrips()));
    }


    public static List<GuideDto> mapGuidesToDtos(List<Guide> guides) {
        return guides.stream()
                .map(GuideDtoMapper::mapGuideToDto)
                .toList();
    }

   /* public static List<GuideNameDto> mapGuidesToDtosWithNames(List<Guide> guides) {
        return guides.stream()
                .map(guide -> new GuideNameDto(guide.getId(), guide.getFirstName(), guide.getLastName()))
                .toList();
    }*/
}
