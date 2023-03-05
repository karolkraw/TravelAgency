package com.example.travelagency.integration;

import com.example.travelagency.destination.Destination;
import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.user.AppUser;
import com.example.travelagency.user.AppUserRepository;
import com.example.travelagency.user.AppUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DestinationIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    HttpHeaders headers;

    private DestinationDto destinationDto;

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

        Long id = 1L;
        destinationDto = new DestinationDto(id, "Paris");
    }

    @Test
    public void fullHappyPathIntegrationTest() {
        //given
        //when
        ResponseEntity<DestinationDto> postResponse = restTemplate.exchange(
                "/destinations/",
                HttpMethod.POST,
                new HttpEntity<>(destinationDto, headers),
                DestinationDto.class);

        //then
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertEquals(destinationDto, postResponse.getBody());




        //given
        Long id = postResponse.getBody().getId();

        //when
        ResponseEntity<DestinationDto> getResponse = restTemplate.exchange(
                "/destinations/" + id,
                HttpMethod.GET,
                new HttpEntity<>(destinationDto, headers),
                DestinationDto.class);

        //then
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertEquals(destinationDto, getResponse.getBody());




        //given
        destinationDto.setDestination("New York");

        //when
        ResponseEntity<Void> putResponse = restTemplate.exchange(
                "/destinations/" + id,
                HttpMethod.PUT,
                new HttpEntity<>(destinationDto, headers),
                Void.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, putResponse.getStatusCode());





        //given
        //when
        ResponseEntity<DestinationDto> getResponseAfterUpdate = restTemplate.exchange(
                "/destinations/" + id,
                HttpMethod.GET,
                new HttpEntity<>(destinationDto, headers),
                DestinationDto.class);

        //then
        assertEquals(HttpStatus.OK, getResponseAfterUpdate.getStatusCode());
        assertEquals(destinationDto, getResponseAfterUpdate.getBody());




        //given
        //when
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/destinations/" + id,
                HttpMethod.DELETE,
                new HttpEntity<>(destinationDto, headers),
                Void.class);

        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());




        //given
        //when
        ResponseEntity<DestinationDto> getResponseAfterDelete = restTemplate.exchange(
                "/destinations/" + id,
                HttpMethod.GET,
                new HttpEntity<>(destinationDto, headers),
                DestinationDto.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, getResponseAfterDelete.getStatusCode());
    }
}
