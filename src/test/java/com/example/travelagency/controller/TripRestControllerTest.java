package com.example.travelagency.controller;

import com.example.travelagency.destination.Destination;
import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.exception.GuideNotFoundException;
import com.example.travelagency.exception.TripNotFoundException;
import com.example.travelagency.guide.Guide;
import com.example.travelagency.guide.dto.GuideDto;
import com.example.travelagency.guide.dto.GuideReadDto;
import com.example.travelagency.trip.Trip;
import com.example.travelagency.trip.TripAdminRestController;
import com.example.travelagency.trip.TripService;
import com.example.travelagency.trip.dto.TripDto;
import com.example.travelagency.trip.dto.TripReadDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class TripRestControllerTest {
    @Mock
    private TripService tripService;

    @InjectMocks
    private TripAdminRestController tripAdminRestController;

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    Guide guide;
    GuideDto guideDto;
    Guide guide2;
    Trip trip;
    Trip trip2;
    TripDto tripDto;
    TripReadDto tripReadDto;
    TripDto tripDto2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(tripAdminRestController).build();
        objectMapper.registerModule(new JavaTimeModule());


        Long id = 1L;

        tripDto = TripDto.builder()
                .id(5L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(1L, "Paris"))
                .guide(new GuideReadDto(id, "John", "Miller"))
                .build();
        tripReadDto = TripReadDto.builder()
                .id(5L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(1L, "Paris")).build();

        trip2 = Trip.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "London")).build();
        tripDto2 = TripDto.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(1L, "London")).build();

        guide = new Guide(id, "John", "Miller", List.of(trip2));
        guideDto = new GuideDto(id, "John", "Miller", List.of(tripReadDto));
        guide2 = new Guide(id, "George", "Harris", List.of(trip2));

        trip = Trip.builder()
                .id(5L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "Paris"))
                .guide(guide)
                .build();
    }


    @Test
    public void shouldReturnTripDtoWhenValidIdExists() throws Exception {
        //given
        Long id = 1L;

        RequestBuilder requestBuilder = get("/admin/trips/" + id);

        given(tripService.getTrip(id)).willReturn(trip);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(tripDto));
    }

    @Test
    public void shouldThrowTripNotFoundExceptionWhenValidIdNotExistsInGetMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = get("/admin/trips/" + id);

        given(tripService.getTrip(id)).willThrow(TripNotFoundException.class);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnAllTripsAndStatusOk() throws Exception {
        //given
        List<Trip> trips = List.of(trip, trip2);
        List<TripDto> tripsDto = List.of(tripDto, tripDto2);

        RequestBuilder requestBuilder = get("/admin/trips/");
        given(tripService.getAllTripsWithUsers(0)).willReturn(trips);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(tripsDto));
    }

    @Test
    public void shouldReturnStatusCreatedInAddMethod() throws Exception {
        //given
        RequestBuilder requestBuilder = post("/admin/trips/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripDto));

        given(tripService.addTrip(any(Trip.class))).willReturn(trip);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(tripDto));
    }

    @Test
    public void shouldReturnNoContentStatusWhenUpdatedProperly() throws Exception {
        //given
        long id = 5L;
        trip = Trip.builder()
                .id(id).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "Paris"))
                .guide(new Guide(1L, "John", "Miller", List.of()))
                .build();

        RequestBuilder requestBuilder = put("/admin/trips/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripDto));

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        verify(tripService).updateTrip(trip);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeletedProperly() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/admin/trips/" + id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowTripNotFoundExceptionWhenValidIdNotExistsInDeleteMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/admin/trips/" + id);

        doThrow(GuideNotFoundException.class).when(tripService).deleteTrip(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
