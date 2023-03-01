package com.example.travelagency.destination;

import javax.validation.Valid;

import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.destination.dto.DestinationDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.example.travelagency.destination.dto.DestinationDtoMapper.*;

@RestController
@RequestMapping("/destinations/")
@RequiredArgsConstructor
public class DestinationRestController {
    private final static Long EMPTY_ID = null;
    @Value("${app.url}")
    private String appUrl;

    private final DestinationService destinationService;

    @GetMapping("/get/{id}")
    public ResponseEntity<DestinationDto> getDestination(@PathVariable Long id) {
        DestinationDto destinationDto = mapDestinationToDto(destinationService.getDestination(id));
        return ResponseEntity
                .ok(destinationDto);
    }

    @GetMapping("/get")
    public ResponseEntity<List<DestinationDto>> getAllDestinations() {
        List<DestinationDto> destinationsDto = mapDestinationsToDtos(destinationService.getAllDestinations());
        return ResponseEntity
                .ok(destinationsDto);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addDestination(@RequestBody @Valid DestinationDto destinationDto) {
        Destination destination = destinationService.addDestination(mapDtoToDestination(EMPTY_ID, destinationDto));

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(appUrl + "destinations/get/{id}")
                .buildAndExpand(destination.getId());

        return ResponseEntity.created(uriComponents.toUri())
                .build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateDestination(@PathVariable Long id, @RequestBody @Valid DestinationDto destinationDto) {
        destinationService.updateDestination(DestinationDtoMapper.mapDtoToDestination(id, destinationDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }


    // get all destinations that were visited most often
    // get all destinations that were visited most often in specified year
    /*@GetMapping("/get/{year}")
    public ResponseEntity<List<Destination>> getAllDestinations(@PathVariable int year) {
        List<Destination> destinations = destinationService.getAllDestinations();
        return ResponseEntity.ok(destinations);
    }*/

}
