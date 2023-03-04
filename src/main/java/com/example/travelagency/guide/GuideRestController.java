package com.example.travelagency.guide;

import com.example.travelagency.exception.GuideNotFoundException;
import com.example.travelagency.guide.dto.GuideDto;
import com.example.travelagency.trip.dto.TripDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.travelagency.guide.dto.GuideDtoMapper.*;
import static com.example.travelagency.trip.dto.TripDtoMapper.mapDtoToTrip;

@RestController
@RequestMapping("/guides")
@RequiredArgsConstructor
public class GuideRestController {
    private static final Long EMPTY_ID = 0L;
    private final GuideService guideService;

    @GetMapping("/{id}")
    public ResponseEntity<GuideDto> getGuide(@PathVariable Long id) {
        Guide guide = guideService.getGuide(id).orElseThrow(() -> new GuideNotFoundException(id));
        GuideDto guideDto = mapGuideToDto(guide);
        return ResponseEntity.ok(guideDto);
    }

    @GetMapping
    public ResponseEntity<List<GuideDto>> getAllGuides() {
        List<GuideDto> guidesDto = mapGuidesToDtos(guideService.getAllGuides());
        return ResponseEntity.ok(guidesDto);
    }

    @PostMapping
    public ResponseEntity<GuideDto> addGuide(@RequestBody @Valid GuideDto guideDto) {
        Guide guide = guideService.addGuide(mapDtoToGuide(EMPTY_ID, guideDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapGuideToDto(guide));
    }

    @PostMapping("/{guideId}")
    public ResponseEntity<GuideDto> addTripToGuide(@PathVariable Long guideId, @RequestBody @Valid TripDto tripDto) {
        Guide guide = guideService.addTripToGuide(guideId, mapDtoToTrip(tripDto.getId(), tripDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapGuideToDto(guide));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGuide(@PathVariable Long id, @RequestBody @Valid GuideDto guideDto) {
        guideService.updateGuide(mapDtoToGuide(id, guideDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGuide(@PathVariable Long id) {
        guideService.deleteGuide(id);
        return ResponseEntity.noContent().build();
    }
}
