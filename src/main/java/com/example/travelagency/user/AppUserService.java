package com.example.travelagency.user;

import com.example.travelagency.exception.AppUserNotFoundException;
import com.example.travelagency.exception.TripNotFoundException;
import com.example.travelagency.trip.Trip;
import com.example.travelagency.trip.TripRepository;
import com.example.travelagency.user.AppUser;
import com.example.travelagency.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final TripRepository tripRepository;

    public AppUser getUser(Long id) {
        return appUserRepository.findById(id).orElseThrow();
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public void updateUser(AppUser appUser) {
    }

    public void deleteUser(Long id) {
        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new AppUserNotFoundException(id));
        for(Trip trip: new ArrayList<>(user.getTrips())) {
            user.removeTrip(trip);
        }
        appUserRepository.deleteById(id);
    }

    @Transactional
    public AppUser addTripToUser(Long userId, Long tripId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new AppUserNotFoundException(userId));
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        user.addTrip(trip);
        return appUserRepository.save(user);
    }
}
