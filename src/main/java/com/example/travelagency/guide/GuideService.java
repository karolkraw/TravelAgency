package com.example.travelagency.guide;

import com.example.travelagency.exception.GuideNotFoundException;
import com.example.travelagency.guide.Guide;
import com.example.travelagency.trip.Trip;
import com.example.travelagency.guide.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuideService {
    private final GuideRepository guideRepository;

    public Guide addGuide(Guide guide) {
        guide.getTrips().forEach(a -> a.setGuide(guide));
        return guideRepository.save(guide);
    }



    public void updateGuide(Guide guide) {
        Guide guideUpdated = guideRepository.findById(guide.getId())
                .orElseThrow(() -> new GuideNotFoundException(guide.getId()));
        guideUpdated.setFirstName(guide.getFirstName());
        guideUpdated.setLastName(guide.getLastName());
        guideRepository.save(guideUpdated);
    }

    public Guide addTripToGuide(Long guideId, Trip trip) {
        /*Guide guide = guideRepository.findById(guideId).orElseThrow("guide not found exception");
        trip.setGuide(guide);
        guide.getTrips().add(trip);*/
        //tripRepository.save(trip); -> dziala dirty checking?
        //guideRepository.save(guide); -> dziala dirty checking?
        //return guide;
        return new Guide();
    }

    public Optional<Guide> getGuide(Long id) {
        return guideRepository.findById(id);
    }

    public List<Guide> getAllGuides() {
        return guideRepository.findAll();
    }

    public void deleteGuide(Long id) {
        guideRepository.deleteById(id);
    }
}
