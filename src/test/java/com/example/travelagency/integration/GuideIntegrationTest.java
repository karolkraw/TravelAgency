package com.example.travelagency.integration;

import com.example.travelagency.destination.Destination;
import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.guide.Guide;
import com.example.travelagency.guide.dto.GuideDto;
import com.example.travelagency.guide.dto.GuideDtoMapper;
import com.example.travelagency.guide.dto.GuideReadDto;
import com.example.travelagency.trip.Trip;
import com.example.travelagency.trip.dto.TripDto;
import com.example.travelagency.trip.dto.TripReadDto;
import com.example.travelagency.user.AppUser;
import com.example.travelagency.user.AppUserRepository;
import com.example.travelagency.user.AppUserRole;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GuideIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    AppUserRepository appUserRepository;


    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    HttpHeaders headers;

    GuideDto guideDto;
    Trip trip;
    Trip trip2;
    TripDto tripDto;
    TripDto tripDto2;
    TripReadDto tripReadDto;


    @BeforeEach
    public void setup() {
        AppUser admin = AppUser.builder().firstName("admin").lastName("admin").passportNumber("123456789").email("admin@example.com")
                .password(passwordEncoder.encode("admin")).appUserRole(AppUserRole.ADMIN)
                .locked(false).enabled(true).trips(null).build();
        appUserRepository.save(admin);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(admin.getEmail(), "admin");
        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList(new BasicAuthenticationInterceptor(admin.getEmail(), "admin")));


        trip = Trip.builder()
                .id(1L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "Paris")).build();
        tripDto = TripDto.builder()
                .id(1L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(0L, "Paris"))
                .guide(new GuideReadDto(1L, "John", "Miller"))
                .build();
        tripReadDto = TripReadDto.builder()
                .id(1L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(1L, "Paris")).build();

        trip2 = Trip.builder()
                .id(2L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(2L, "London")).build();
        tripDto2 = TripDto.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(0L, "London"))
                .guide(new GuideReadDto(1L, "Gary", "Miller"))
                .build();


        Long id = 1L;

        guideDto = new GuideDto(id, "John", "Miller", List.of(tripReadDto));
    }


    @Test
    public void fullHappyPathIntegrationTest() {
        //given
        //when
        ResponseEntity<GuideDto> postResponse = restTemplate.exchange(
                "/guides/",
                HttpMethod.POST,
                new HttpEntity<>(guideDto, headers),
                GuideDto.class);

        //then
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertEquals(guideDto, postResponse.getBody());







        //given
        Long id = postResponse.getBody().getId();

        //when
        ResponseEntity<GuideDto> getResponse = restTemplate.exchange(
                "/guides/" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GuideDto.class);

        //then
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(guideDto, getResponse.getBody());




        //given
        guideDto.setFirstName("George");
        guideDto.setLastName("Harris");

        //when
        ResponseEntity<Void> putResponse = restTemplate.exchange(
                "/guides/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(guideDto, headers),
                Void.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, putResponse.getStatusCode());




        //given
        //when
        ResponseEntity<GuideDto> getResponseAfterUpdate = restTemplate.exchange(
                "/guides/" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GuideDto.class);

        //then
        assertEquals(HttpStatus.OK, getResponseAfterUpdate.getStatusCode());
        assertEquals(guideDto, getResponseAfterUpdate.getBody());




        //given
        //when
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/guides/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());




        //given
        //when
        ResponseEntity<GuideDto> getResponseAfterDelete = restTemplate.exchange(
                "/guides/" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GuideDto.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, getResponseAfterDelete.getStatusCode());




        //given
        //when
        ResponseEntity<GuideDto> postGuideResponse = restTemplate.exchange(
                "/guides/",
                HttpMethod.POST,
                new HttpEntity<>(guideDto, headers),
                GuideDto.class);

        //then
        assertNotNull(postGuideResponse.getBody());

        //given
        Long guideId = postGuideResponse.getBody().getId();

        //when
        ResponseEntity<GuideDto> guideResponse = restTemplate.exchange(
                "/guides/" + guideId,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                GuideDto.class);

        //then
        assertNotNull(guideResponse.getBody());


        GuideDto guideDtoResponse = guideResponse.getBody();
        Guide guideMapped = GuideDtoMapper.mapDtoToGuide(guideDtoResponse.getId(), guideDtoResponse);

        guideMapped.setTrips(List.of(trip, trip2));
        GuideDto guideDtoMapped = GuideDtoMapper.mapGuideToDto(guideMapped);


        //when
        ResponseEntity<GuideDto> postAddTripResponse = restTemplate.exchange(
                "/guides/" + guideId,
                HttpMethod.POST,
                new HttpEntity<>(tripDto2, headers),
                GuideDto.class);

        //then
        assertEquals(HttpStatus.CREATED, postAddTripResponse.getStatusCode());
        assertEquals(guideDtoMapped, postAddTripResponse.getBody());
    }
}



