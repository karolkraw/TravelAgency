package com.example.travelagency.controller;

import com.example.travelagency.controller.dto.destination.DestinationDto;
import com.example.travelagency.controller.dto.guide.GuideDto;
import com.example.travelagency.controller.dto.guide.GuideReadDto;
import com.example.travelagency.controller.dto.trip.TripDto;
import com.example.travelagency.controller.dto.trip.TripReadDto;
import com.example.travelagency.exception.GuideNotFoundException;
import com.example.travelagency.exception.TripNotFoundException;
import com.example.travelagency.model.Destination;
import com.example.travelagency.model.Guide;
import com.example.travelagency.model.Trip;
import com.example.travelagency.service.TripService;
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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class TripRestControllerTest {
    @MockBean
    private TripService tripService;

    @Autowired
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
        objectMapper.registerModule(new JavaTimeModule());

        Long id = 1L;

        tripDto = TripDto.builder()
                .id(5L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destinationDto(new DestinationDto(1L, "Paris"))
                .guideReadDto(new GuideReadDto(id, "John", "Miller"))
                .build();
        tripReadDto = TripReadDto.builder()
                .id(5L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destinationDto(new DestinationDto(1L, "Paris")).build();

        trip2 = Trip.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "London")).build();
        tripDto2 = TripDto.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destinationDto(new DestinationDto(1L, "London")).build();

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

        RequestBuilder requestBuilder = get("/trips/get/" + id);

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
        RequestBuilder requestBuilder = get("/trips/get/" + id);

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

        RequestBuilder requestBuilder = get("/trips/get/");
        given(tripService.getAllTrips()).willReturn(trips);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(tripsDto));
    }

    @Test
    public void shouldReturnStatusCreatedInAddMethod() throws Exception {
        //given
        Trip tripWithNullId = Trip.builder()
                .id(null).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "Paris"))
                .guide(new Guide(1L, "John", "Miller", List.of()))
                .build();

        RequestBuilder requestBuilder = post("/trips/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripDto));

        given(tripService.addTrip(tripWithNullId)).willReturn(trip);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/trips/get/{id}") // change it to get value from properties
                .buildAndExpand(trip.getId());

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        URI expectedUri = uriComponents.toUri();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(expectedUri.toString()).isEqualTo(response.getHeader("location"));
    }

    @Test
    public void shouldReturnNoContentStatusWhenUpdatedProperly() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = put("/trips/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tripDto));

        doNothing().when(tripService).deleteTrip(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeletedProperly() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/trips/delete/" + id);

        doNothing().when(tripService).deleteTrip(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowTripNotFoundExceptionWhenValidIdNotExistsInDeleteMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/trips/delete/" + id);

        doThrow(GuideNotFoundException.class).when(tripService).deleteTrip(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
