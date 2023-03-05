package com.example.travelagency.integration;

import com.example.travelagency.destination.Destination;
import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.guide.dto.GuideDto;
import com.example.travelagency.guide.dto.GuideReadDto;
import com.example.travelagency.trip.Trip;
import com.example.travelagency.trip.dto.TripDto;
import com.example.travelagency.trip.dto.TripReadDto;
import com.example.travelagency.user.AppUser;
import com.example.travelagency.user.AppUserRepository;
import com.example.travelagency.user.AppUserRole;
import com.example.travelagency.user.dto.AppUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MainIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    HttpHeaders headers;

    AppUser user1;
    AppUser user2;
    AppUser user3;
    AppUserDto userDto1;
    TripDto tripDto1;
    TripReadDto tripReadDto1;

    @BeforeEach
    public void setup() {
        AppUser admin = AppUser.builder().firstName("admin").lastName("admin").passportNumber("123456789").email("admin@example.com")
                .password(passwordEncoder.encode("admin")).appUserRole(AppUserRole.ADMIN)
                .locked(false).enabled(true).trips(new ArrayList<>()).build();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(admin.getEmail(), "admin");
        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList(new BasicAuthenticationInterceptor(admin.getEmail(), "admin")));

        user1 = AppUser.builder().firstName("Jane").lastName("Doe").passportNumber("AB12345")
                .email("jane@example.com").password(passwordEncoder.encode("password123"))
                .appUserRole(AppUserRole.USER).locked(false).enabled(true).build();

        userDto1 = AppUserDto.builder().id(2L).firstName("Jane").lastName("Doe")
                .passportNumber("AB12345").email("jane@example.com")
                .appUserRole(AppUserRole.USER).locked(false).enabled(true).build();

        appUserRepository.save(admin);
        appUserRepository.save(user1);

        user2 = AppUser.builder().firstName("Bob").lastName("Smith").passportNumber("B7654321").email("bob@example.com")
                .password(passwordEncoder.encode("password123")).appUserRole(AppUserRole.USER)
                .locked(false).enabled(true).trips(new ArrayList<>()).build();

        user3 = AppUser.builder().firstName("John").lastName("Doe").passportNumber("C9876543").email("john@example.com")
                .password(passwordEncoder.encode("password123")).appUserRole(AppUserRole.USER)
                .locked(false).enabled(true).trips(new ArrayList<>()).build();


        Destination destination1 = Destination.builder().id(1L).destination("Paris").build();
        DestinationDto destinationDto1 = DestinationDto.builder().id(1L).destination("Paris").build();

        Destination destination2 = Destination.builder().id(2L).destination("London").build();
        DestinationDto destinationDto2 = DestinationDto.builder().id(2L).destination("London").build();


        tripDto1 = TripDto.builder().id(1L).price(BigDecimal.valueOf(1000))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(destinationDto1).build();

        tripReadDto1 = TripReadDto.builder().id(1L).price(BigDecimal.valueOf(1000))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(destinationDto1).build();
    }

    @Test
    void fullHappyPathIntegrationTest() {
        GuideDto guide = GuideDto.builder().id(1L).firstName("Emily").lastName("Brown").trips(new ArrayList<>()).build();

        ResponseEntity<GuideDto> postGuideResponse = restTemplate.exchange(
                "/guides/",
                HttpMethod.POST,
                new HttpEntity<>(guide, headers),
                GuideDto.class);

        ResponseEntity<GuideDto> postAddTripResponse = restTemplate.exchange(
                "/guides/" + postGuideResponse.getBody().getId(),
                HttpMethod.POST,
                new HttpEntity<>(tripDto1, headers),
                GuideDto.class);

        GuideReadDto guideReadDto = GuideReadDto.builder().id(guide.getId()).firstName(guide.getFirstName()).lastName(guide.getLastName()).build();
        tripDto1.setGuide(guideReadDto);
        guide.getTrips().add(tripReadDto1);

        assertEquals(HttpStatus.CREATED, postAddTripResponse.getStatusCode());
        assertEquals(guide, postAddTripResponse.getBody());
    }
}

