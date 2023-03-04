package com.example.travelagency.integration;

import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.guide.dto.GuideReadDto;
import com.example.travelagency.trip.dto.TripDto;
import com.example.travelagency.trip.dto.TripRequestDto;
import com.example.travelagency.user.AppUser;
import com.example.travelagency.user.AppUserRepository;
import com.example.travelagency.user.AppUserRole;
import com.example.travelagency.user.dto.AppUserReadDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TripIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AppUserRepository appUserRepository;


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    HttpHeaders headers;

    TripRequestDto trip1;
    TripRequestDto trip2;
    TripDto tripDto1;
    TripDto tripDto2;

    AppUser user1;
    AppUserReadDto userDto1;

    @BeforeEach
    public void setup() {
        AppUser admin = AppUser.builder().firstName("admin").lastName("admin").passportNumber("123456789").email("admin@example.com")
                .password(new BCryptPasswordEncoder().encode("admin")).appUserRole(AppUserRole.ADMIN)
                .locked(false).enabled(true).trips(null).build();
        appUserRepository.save(admin);

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(admin.getEmail(), "admin");
        restTemplate.getRestTemplate().setInterceptors(Collections.singletonList(new BasicAuthenticationInterceptor(admin.getEmail(), "admin")));

        DestinationDto destination1 = DestinationDto.builder()
                .id(1L)
                .destination("Paris")
                .build();

        GuideReadDto guide1 = GuideReadDto.builder()
                .id(1L)
                .firstName("John")
                .lastName("Smith")
                .build();

        user1 = AppUser.builder()
                .firstName("Jane")
                .lastName("Doe")
                .passportNumber("AB12345")
                .email("jane@example.com")
                .password(new BCryptPasswordEncoder().encode("admin"))
                .appUserRole(AppUserRole.USER)
                .locked(false)
                .enabled(true)
                .build();

        userDto1 = AppUserReadDto.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Doe")
                .passportNumber("AB12345")
                .email("jane@example.com")
                .appUserRole(AppUserRole.USER)
                .locked(false)
                .enabled(true)
                .build();

        AppUserReadDto user1 = AppUserReadDto.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Doe")
                .passportNumber("AB12345")
                .email("jane@example.com")
                .appUserRole(AppUserRole.USER)
                .locked(false)
                .enabled(true)
                .build();

        AppUserReadDto user2 = AppUserReadDto.builder()
                .id(2L)
                .firstName("Bob")
                .lastName("Johnson")
                .passportNumber("CD67890")
                .email("bob@example.com")
                .appUserRole(AppUserRole.USER)
                .locked(false)
                .enabled(true)
                .build();

        List<AppUserReadDto> users1 = new ArrayList<>();
        users1.add(user1);
        users1.add(user2);

        trip1 = TripRequestDto.builder()
                .id(1L)
                .price(BigDecimal.valueOf(1000))
                .departureDate(LocalDate.of(2023, 6, 10))
                .returnDate(LocalDate.of(2023, 6, 16))
                .destination(destination1)
                .guide(guide1)
                .build();

        tripDto1 = TripDto.builder()
                .id(1L)
                .price(BigDecimal.valueOf(1000))
                .departureDate(LocalDate.of(2023, 6, 10))
                .returnDate(LocalDate.of(2023, 6, 16))
                .destination(destination1)
                .guide(guide1)
                .users(new ArrayList<>())
                .build();

        DestinationDto destination2 = DestinationDto.builder()
                .id(2L)
                .destination("London")
                .build();

        GuideReadDto guide2 = GuideReadDto.builder()
                .id(2L)
                .firstName("Emily")
                .lastName("Brown")
                .build();

        AppUserReadDto user3 = AppUserReadDto.builder()
                .id(3L)
                .firstName("Tom")
                .lastName("Jones")
                .passportNumber("EF12345")
                .email("tom@example.com")
                .appUserRole(AppUserRole.USER)
                .locked(false)
                .enabled(true)
                .build();

        List<AppUserReadDto> users2 = new ArrayList<>();
        users2.add(user3);

        trip2 = TripRequestDto.builder()
                .id(2L)
                .price(BigDecimal.valueOf(2000))
                .departureDate(LocalDate.of(2023, 7, 1))
                .returnDate(LocalDate.of(2023, 7, 7))
                .destination(destination2)
                .guide(guide2)
                .build();

        tripDto2 = TripDto.builder()
                .id(2L)
                .price(BigDecimal.valueOf(2000))
                .departureDate(LocalDate.of(2023, 7, 1))
                .returnDate(LocalDate.of(2023, 7, 7))
                .destination(destination2)
                .guide(guide2)
                .users(new ArrayList<>())
                .build();
    }



    @Test
    void fullHappyPathIntegrationTest() {
        //given
        //when
        ResponseEntity<TripDto> postResponse = restTemplate.exchange(
                "/admin/trips/",
                HttpMethod.POST,
                new HttpEntity<>(trip1, headers),
                TripDto.class);

        //then
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertEquals(tripDto1, postResponse.getBody());




        //given
        Long id = postResponse.getBody().getId();

        //when
        ResponseEntity<TripDto> getResponse = restTemplate.exchange(
                "/admin/trips/{id}",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                TripDto.class,
                id);

        //then
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(tripDto1, getResponse.getBody());





        //given
        //when
        ResponseEntity<TripDto> postResponseEntity = restTemplate.exchange(
                "/admin/trips/",
                HttpMethod.POST,
                new HttpEntity<>(trip2, headers),
                TripDto.class);

        //then
        assertEquals(HttpStatus.CREATED, postResponseEntity.getStatusCode());
        assertEquals(tripDto2, postResponseEntity.getBody());




        //given
        List<TripDto> list = new ArrayList<>();
        list.add(tripDto1);
        list.add(tripDto2);

        //when
        ResponseEntity<List<TripDto>> getResponseEntity = restTemplate.exchange(
                "/admin/trips/",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {},
                id);

        //then
        assertEquals(HttpStatus.OK, getResponseEntity.getStatusCode());
        assertEquals(list, getResponseEntity.getBody());




        //given
        trip1.setPrice(BigDecimal.valueOf(200.00));
        trip1.setReturnDate(LocalDate.now().plusDays(21));
        tripDto1.setPrice(BigDecimal.valueOf(200.00));
        tripDto1.setReturnDate(LocalDate.now().plusDays(21));



        //when
        ResponseEntity<Void> putResponse = restTemplate.exchange(
                "/admin/trips/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(trip1, headers),
                Void.class,
                id);

        //then
        assertEquals(HttpStatus.NO_CONTENT, putResponse.getStatusCode());




        //given
        //when
        ResponseEntity<TripDto> getResponseAfterUpdate = restTemplate.exchange(
                "/admin/trips/{id}",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                TripDto.class,
                id);

        //then
        assertEquals(HttpStatus.OK, getResponseAfterUpdate.getStatusCode());
        assertEquals(tripDto1, getResponseAfterUpdate.getBody());




        //given
        user1 = appUserRepository.save(user1);
        tripDto1.setUsers(new ArrayList<>());
        tripDto1.getUsers().add(userDto1);

        //when
        ResponseEntity<TripDto> postAddUserResponseEntity = restTemplate.exchange(
                "/admin/trips/{tripId}/users/{userId}",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                TripDto.class,
                1L,
                user1.getId());

        //then
        assertEquals(HttpStatus.CREATED, postAddUserResponseEntity.getStatusCode());
        assertEquals(tripDto1, postAddUserResponseEntity.getBody());




        //given
        //when
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/admin/trips/{id}",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                Void.class,
                id);

        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());




        //given
        //when
        ResponseEntity<TripDto> getResponseAfterDelete = restTemplate.exchange(
                "/admin/trips/{id}",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                TripDto.class,
                id);

        //then
        assertEquals(HttpStatus.NOT_FOUND, getResponseAfterDelete.getStatusCode());
    }
}
