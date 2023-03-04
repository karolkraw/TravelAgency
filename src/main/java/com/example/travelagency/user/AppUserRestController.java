
package com.example.travelagency.user;


import com.example.travelagency.user.dto.AppUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.travelagency.user.dto.AppUserDtoMapper.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserRestController {
    private final AppUserService appUserService;

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getUser(@PathVariable Long id) {
        AppUser user = appUserService.getUser(id);
        return ResponseEntity.ok(mapAppUserToDto(user));
    }

    @GetMapping
    public ResponseEntity<List<AppUserDto>> getAllUsers() {
        List<AppUser> users = appUserService.getAllUsers();
        return ResponseEntity.ok(mapAppUsersToDtos(users));
    }

    @PostMapping("/{userId}/users/{tripId}")
    public ResponseEntity<AppUserDto> addTripToUser(@PathVariable Long userId, @PathVariable Long tripId) {
        AppUser user = appUserService.addTripToUser(userId, tripId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapAppUserToDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAppUser(@PathVariable Long id, @RequestBody @Valid AppUserDto userDto) {
        appUserService.updateUser(mapDtoToAppUser(id, userDto));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        appUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}







