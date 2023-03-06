package com.example.travelagency.guide;

import com.example.travelagency.exception.GuideNotFoundException;
import com.example.travelagency.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuideService {
    private final GuideRepository guideRepository;

    public Guide addGuide(Guide guide) {
        List<Trip> trips = guide.getTrips();
        if(trips != null)
            trips.forEach(a -> a.setGuide(guide));
        return guideRepository.save(guide);
    }

    @Transactional
    public void updateGuide(Guide guide) {
        Guide guideUpdated = guideRepository.findById(guide.getId())
                .orElseThrow(() -> new GuideNotFoundException(guide.getId()));
        guideUpdated.setFirstName(guide.getFirstName());
        guideUpdated.setLastName(guide.getLastName());
        guideRepository.save(guideUpdated);
    }

    @Transactional
    public Guide addTripToGuide(Long guideId, Trip trip) {
        Guide guide = guideRepository.findById(guideId).orElseThrow(() -> new GuideNotFoundException(guideId));
        trip.setGuide(guide);
        guide.getTrips().add(trip);
        return guideRepository.save(guide);
    }

    public Optional<Guide> getGuide(Long id) {
        return guideRepository.findById(id);
    }

    public List<Guide> getAllGuides() {
        return guideRepository.findAll();
    }

    @Transactional
    public void deleteGuide(Long id) {
        Guide guide = guideRepository.findById(id).orElseThrow(() -> new GuideNotFoundException(id));
        for(Trip trip: new ArrayList<>(guide.getTrips())) {
            guide.removeTrip(trip);
        }
        guideRepository.deleteById(id);
    }
}
