package com.example.travelagency.user;

import com.example.travelagency.user.AppUser;
import com.example.travelagency.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUser addUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

    public AppUser getUser(Long id) {
        return appUserRepository.findById(id).orElseThrow();
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    public void updateUser(AppUser appUser) {
    }

    public void deleteUser(Long id) {
        appUserRepository.deleteById(id);
    }
}