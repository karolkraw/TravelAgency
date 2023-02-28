package com.example.travelagency.service;

import com.example.travelagency.model.Guide;
import com.example.travelagency.model.Trip;
import com.example.travelagency.repository.GuideRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GuideService {
    private final GuideRepository guideRepository;

    public Guide addGuide(Guide guide) {
        guide.getTrips().forEach(a -> a.setGuide(guide));
        return guideRepository.save(guide);
    }

    public Guide removeGuide(Long id) {
        return new Guide();
    }

    public void updateGuide(Guide guide) {
        Guide guideUpdated = guideRepository.findById(guide.getId()).orElseThrow();
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

    public Guide getGuide(Long id) {
        return new Guide();
    }

    public List<Guide> getAllGuides() {
        return new ArrayList<>();
    }

    public void deleteGuide(Long id) {
    }
}
