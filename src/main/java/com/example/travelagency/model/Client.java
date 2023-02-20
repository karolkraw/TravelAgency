package com.example.travelagency.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "first name is required")
    @Size(min = 1, max = 50, message = "first name must be between 1 and 50 characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 1, max = 50, message = "last name must be between 1 and 50 characters")
    private String lastName;

    @NotBlank(message = "passport number is required")
    private String passportNumber;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private Set<Trip> trips = new HashSet<>();
}
