package com.example.travelagency.controller;

import com.example.travelagency.destination.Destination;
import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.exception.AppUserNotFoundException;
import com.example.travelagency.trip.Trip;
import com.example.travelagency.trip.dto.TripReadDto;
import com.example.travelagency.user.AppUser;
import com.example.travelagency.user.AppUserRestController;
import com.example.travelagency.user.AppUserRole;
import com.example.travelagency.user.AppUserService;
import com.example.travelagency.user.dto.AppUserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class AppUserRestControllerTest {
    @Mock
    private AppUserService appUserService;

    @InjectMocks
    private AppUserRestController appUserRestController;

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    AppUser user;
    AppUser user2;
    AppUserDto userDto;
    AppUserDto userDto2;
    Trip trip;
    Trip trip2;
    TripReadDto tripReadDto;
    TripReadDto tripReadDto2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(appUserRestController).build();
        objectMapper.registerModule(new JavaTimeModule());

        trip = Trip.builder()
                .id(5L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "Paris"))
                .build();
        trip2 = Trip.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "London")).build();

        tripReadDto = TripReadDto.builder()
                .id(5L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(1L, "Paris")).build();
        tripReadDto2 = TripReadDto.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(1L, "London")).build();

        user = AppUser.builder().id(1L).firstName("John").lastName("Miller")
                .passportNumber("abc1234").appUserRole(AppUserRole.USER)
                .email("johnMil4567@gmail.com").password("mypassword")
                .locked(false).enabled(true).trips(List.of(trip, trip2)).build();
        user2 = AppUser.builder().id(2L).firstName("Joshua").lastName("Hamilton")
                .passportNumber("acb4567").appUserRole(AppUserRole.USER)
                .email("jashHamilton123@gmail.com").password("password")
                .locked(false).enabled(true).trips(List.of(trip, trip2)).build();

        userDto = AppUserDto.builder().id(1L).firstName("John").lastName("Miller")
                .passportNumber("abc1234").appUserRole(AppUserRole.USER)
                .email("johnMil4567@gmail.com")
                .locked(false).enabled(true).trips(List.of(tripReadDto, tripReadDto2)).build();
        userDto2 = AppUserDto.builder().id(2L).firstName("Joshua").lastName("Hamilton")
                .passportNumber("acb4567").appUserRole(AppUserRole.USER)
                .email("jashHamilton123@gmail.com")
                .locked(false).enabled(true).trips(List.of(tripReadDto, tripReadDto2)).build();
    }

    @Test
    public void shouldReturnAppUserDtoWhenValidIdExists() throws Exception {
        //given
        Long id = 1L;

        RequestBuilder requestBuilder = get("/users/" + id);

        given(appUserService.getUser(id)).willReturn(user);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(userDto));
    }

    @Test
    public void shouldThrowAppUserNotFoundExceptionWhenValidIdNotExistsInGetMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = get("/users/" + id);

        given(appUserService.getUser(id)).willThrow(AppUserNotFoundException.class);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnAllUsersAndStatusOk() throws Exception {
        //given
        List<AppUser> users = List.of(user, user2);
        List<AppUserDto> usersDto = List.of(userDto, userDto2);

        RequestBuilder requestBuilder = get("/users/");
        given(appUserService.getAllUsers()).willReturn(users);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(usersDto));
    }

    @Test
    public void shouldReturnNoContentStatusWhenUpdatedProperly() throws Exception {
        //given
        long id = 1L;
        user.setPassword(null);
        RequestBuilder requestBuilder = put("/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto));


        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        verify(appUserService).updateUser(user);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeletedProperly() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/users/" + id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        verify(appUserService).deleteUser(id);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowAppUserNotFoundExceptionWhenValidIdNotExistsInDeleteMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/users/" + id);

        doThrow(AppUserNotFoundException.class).when(appUserService).deleteUser(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


}