package com.example.travelagency.destination;

import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.destination.dto.DestinationDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.travelagency.destination.dto.DestinationDtoMapper.*;

@RestController
@RequestMapping("/destinations/")
@RequiredArgsConstructor
public class DestinationRestController {
    private final static Long EMPTY_ID = 0L;
    private final DestinationService destinationService;

    @GetMapping("/{id}")
    public ResponseEntity<DestinationDto> getDestination(@PathVariable Long id) {
        DestinationDto destinationDto = mapDestinationToDto(destinationService.getDestination(id));
        return ResponseEntity
                .ok(destinationDto);
    }

    @GetMapping
    public ResponseEntity<List<DestinationDto>> getAllDestinations() {
        List<DestinationDto> destinationsDto = mapDestinationsToDtos(destinationService.getAllDestinations());
        return ResponseEntity
                .ok(destinationsDto);
    }

    @PostMapping
    public ResponseEntity<DestinationDto> addDestination(@RequestBody @Valid DestinationDto destinationDto) {
        Destination destination = destinationService.addDestination(mapDtoToDestination(EMPTY_ID, destinationDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapDestinationToDto(destination));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDestination(@PathVariable Long id, @RequestBody @Valid DestinationDto destinationDto) {
        destinationService.updateDestination(DestinationDtoMapper.mapDtoToDestination(id, destinationDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDestination(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.noContent().build();
    }
}
