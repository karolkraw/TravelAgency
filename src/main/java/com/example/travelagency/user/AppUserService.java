package com.example.travelagency.user;

import com.example.travelagency.exception.AppUserNotFoundException;
import com.example.travelagency.trip.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUser getUser(Long id) {
        return appUserRepository.findById(id).orElseThrow();
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @Transactional
    public void updateUser(AppUser user) {
        AppUser userUpdated = appUserRepository.findById(user.getId())
                .orElseThrow(() -> new AppUserNotFoundException(user.getId()));
        userUpdated.setFirstName(user.getFirstName());
        userUpdated.setLastName(user.getLastName());
        userUpdated.setPassportNumber(user.getPassportNumber());
        appUserRepository.save(userUpdated);
    }

    @Transactional
    public void deleteUser(Long id) {
        AppUser user = appUserRepository.findById(id).orElseThrow(() -> new AppUserNotFoundException(id));
        for (Trip trip : new ArrayList<>(user.getTrips())) {
            user.removeTrip(trip);
        }
        appUserRepository.deleteById(id);
    }

    @Transactional
    public AppUser addTripToUser(Long userId, Trip trip) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new AppUserNotFoundException(userId));
        user.addTrip(trip);
        return appUserRepository.save(user);
    }
}
