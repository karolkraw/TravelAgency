package com.example.travelagency.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.travelagency.user.AppUserRole;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AppUserReadDto {
    private Long id;

    @NotBlank(message = "first name is required")
    @Size(min = 1, max = 50, message = "first name must be between 1 and 50 characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 1, max = 50, message = "last name must be between 1 and 50 characters")
    private String lastName;

    @NotBlank(message = "passport number is required")
    private String passportNumber;

    String email;

    private AppUserRole appUserRole;

    private Boolean locked;
    private Boolean enabled;
}
