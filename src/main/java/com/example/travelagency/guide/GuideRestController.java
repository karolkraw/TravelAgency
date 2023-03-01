package com.example.travelagency.guide;

import com.example.travelagency.guide.dto.GuideDto;
import com.example.travelagency.trip.dto.TripDto;
import com.example.travelagency.exception.GuideNotFoundException;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.example.travelagency.guide.dto.GuideDtoMapper.*;
import static com.example.travelagency.trip.dto.TripDtoMapper.*;

@RestController
@RequestMapping("/guides")
@RequiredArgsConstructor
public class GuideRestController {
    private static final Long EMPTY_ID = null;
    @Value("${app.url}")
    private String appUrl;

    private final GuideService guideService;

    @GetMapping("/get/{id}")
    public ResponseEntity<GuideDto> getGuide(@PathVariable Long id) {
        Guide guide = guideService.getGuide(id).orElseThrow(() -> new GuideNotFoundException(id));
        GuideDto guideDto = mapGuideToDto(guide);
        return ResponseEntity.ok(guideDto);
    }

    @GetMapping("/get")
    public ResponseEntity<List<GuideDto>> getAllGuides() {
        List<GuideDto> guidesDto = mapGuidesToDtos(guideService.getAllGuides());
        return ResponseEntity.ok(guidesDto);
    }

    /*@GetMapping("/names")
    public ResponseEntity<List<GuideNameDto>> getAllGuideNames() {
        List<GuideNameDto> result = mapGuidesToDtosWithNames(guideService.findAll());
        return ResponseEntity.ok(result);
    }*/

    @PostMapping("/add")
    public ResponseEntity<Object> addGuide(@RequestBody @Valid GuideDto guideDto) {
        Guide guide = guideService.addGuide(mapDtoToGuide(EMPTY_ID, guideDto));
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(appUrl + "/guides/get/{id}")
                .buildAndExpand(guide.getId());
        return ResponseEntity.created(uriComponents.toUri())
                .build();
    }

    @PostMapping("/{guideId}/trip")
    public ResponseEntity<Object> addTripToGuide(@PathVariable Long guideId, @RequestBody @Valid TripDto tripDto) {
        Guide guide = guideService.addTripToGuide(guideId, mapDtoToTrip(tripDto.getId(), tripDto));
        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(appUrl + "/guides/get/{id}")
                .buildAndExpand(guide.getId());
        return ResponseEntity.created(uriComponents.toUri())
                .build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateGuide(@PathVariable Long id, @RequestBody @Valid GuideDto guideDto) {
        guideService.updateGuide(mapDtoToGuide(id, guideDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteGuide(@PathVariable Long id) {
        guideService.deleteGuide(id);
        return ResponseEntity.noContent().build();
    }








    // get guide with the most trips
    // get all guides with specified tourGuide
    // get all trips to specified guide
    //

}
