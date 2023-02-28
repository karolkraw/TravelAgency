package com.example.travelagency.controller;

import com.example.travelagency.controller.dto.destination.DestinationDto;
import com.example.travelagency.controller.dto.trip.TripReadDto;
import com.example.travelagency.controller.dto.user.AppUserDto;
import com.example.travelagency.exception.AppUserNotFoundException;
import com.example.travelagency.model.AppUser;
import com.example.travelagency.model.AppUserRole;
import com.example.travelagency.model.Destination;
import com.example.travelagency.model.Trip;
import com.example.travelagency.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN"})
public class AppUserRestControllerTest {
    @MockBean
    private AppUserService appUserService;

    @Autowired
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
                .destinationDto(new DestinationDto(1L, "Paris")).build();
        tripReadDto2 = TripReadDto.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destinationDto(new DestinationDto(1L, "London")).build();

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
                .locked(false).enabled(true).tripReadDtos(List.of(tripReadDto, tripReadDto2)).build();
        userDto2 = AppUserDto.builder().id(2L).firstName("Joshua").lastName("Hamilton")
                .passportNumber("acb4567").appUserRole(AppUserRole.USER)
                .email("jashHamilton123@gmail.com")
                .locked(false).enabled(true).tripReadDtos(List.of(tripReadDto, tripReadDto2)).build();
    }

    @Test
    public void shouldReturnAppUserDtoWhenValidIdExists() throws Exception {
        //given
        Long id = 1L;

        RequestBuilder requestBuilder = get("/users/get/" + id);

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
        RequestBuilder requestBuilder = get("/users/get/" + id);

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

        RequestBuilder requestBuilder = get("/users/get/");
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
        Long id = 1L;
        RequestBuilder requestBuilder = put("/users/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto));

        doNothing().when(appUserService).deleteUser(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeletedProperly() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/users/delete/" + id);

        doNothing().when(appUserService).deleteUser(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowAppUserNotFoundExceptionWhenValidIdNotExistsInDeleteMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/users/delete/" + id);

        doThrow(AppUserNotFoundException.class).when(appUserService).deleteUser(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


}