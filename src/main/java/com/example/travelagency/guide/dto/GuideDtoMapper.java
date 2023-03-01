package com.example.travelagency.guide.dto;

import com.example.travelagency.guide.Guide;

import java.util.List;

import static com.example.travelagency.trip.dto.TripDtoMapper.mapReadDtoTripsToTrips;
import static com.example.travelagency.trip.dto.TripDtoMapper.mapTripsToTripReadDtos;

public class GuideDtoMapper {
    private GuideDtoMapper() {}

    public static GuideDto mapGuideToDto(Guide guide) {
        if(guide == null) return null;
        return new GuideDto(guide.getId(), guide.getFirstName(), guide.getLastName(), mapTripsToTripReadDtos(guide.getTrips()));
    }

    public static GuideReadDto mapGuideToGuideReadDto(Guide guide) {
        if(guide == null) return null;
        return new GuideReadDto(guide.getId(), guide.getFirstName(), guide.getLastName());
    }

    public static Guide mapDtoToGuide(Long id, GuideDto guideDto) {
        if(guideDto == null) return null;
        return new Guide(id, guideDto.getFirstName(), guideDto.getLastName(), mapReadDtoTripsToTrips(guideDto.getTrips()));
    }

    public static Guide mapGuideReadDtoToGuide(Long id, GuideReadDto guide) {
        if(guide == null) return null;
        return new Guide(id, guide.getFirstName(), guide.getLastName(), List.of());
    }


    public static List<GuideDto> mapGuidesToDtos(List<Guide> guides) {
        if(guides == null) return null;
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
