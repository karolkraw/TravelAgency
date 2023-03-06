package com.example.travelagency.destination;

import com.example.travelagency.exception.DestinationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationService {
    private final DestinationRepository destinationRepository;

    public Destination addDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public Destination getDestination(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFoundException(id));
    }

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    @Transactional
    public void deleteDestination(long id) {
        destinationRepository.findById(id)
                .orElseThrow(() -> new DestinationNotFoundException(id));
        destinationRepository.deleteById(id);
    }

    @Transactional
    public void updateDestination(Destination destination) {
        Destination updatedDestination = destinationRepository.findById(destination.getId())
                .orElseThrow(() -> new DestinationNotFoundException(destination.getId()));
        updatedDestination.setDestination(destination.getDestination());
        destinationRepository.save(updatedDestination);
    }
}
