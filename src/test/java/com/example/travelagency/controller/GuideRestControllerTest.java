package com.example.travelagency.controller;

import com.example.travelagency.destination.Destination;
import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.exception.GuideNotFoundException;
import com.example.travelagency.guide.Guide;
import com.example.travelagency.guide.GuideRestController;
import com.example.travelagency.guide.GuideService;
import com.example.travelagency.guide.dto.GuideDto;
import com.example.travelagency.trip.Trip;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class GuideRestControllerTest {
    @Mock
    private GuideService guideService;

    @InjectMocks
    private GuideRestController guideRestController;

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();


    Guide guide;
    Guide guide2;
    GuideDto guideDto;
    GuideDto guideDto2;
    Trip trip;
    Trip trip2;
    TripReadDto tripReadDto;
    TripReadDto tripReadDto2;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(guideRestController).build();
        objectMapper.registerModule(new JavaTimeModule());


        trip = Trip.builder()
                .id(5L).price(BigDecimal.valueOf(150L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "Paris")).build();
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
        tripReadDto2 = TripReadDto.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new DestinationDto(1L, "London")).build();

        Long id = 1L;

        guide = new Guide(id, "John", "Miller", List.of(trip, trip2));
        guideDto = new GuideDto(id, "John", "Miller", List.of(tripReadDto, tripReadDto2));
        guide2 = new Guide(id, "George", "Harris", List.of(trip, trip2));
        guideDto2 = new GuideDto(id, "George", "Harris", List.of(tripReadDto, tripReadDto2));
    }


    @Test
    public void shouldReturnGuideDtoWhenValidIdExists() throws Exception {
        //given
        Long id = 1L;

        RequestBuilder requestBuilder = get("/guides/" + id);

        given(guideService.getGuide(id)).willReturn(Optional.ofNullable(guide));

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(guideDto));
    }

    @Test
    public void shouldReturnAllGuidesAndStatusOk() throws Exception {
        //given
        List<Guide> guides = List.of(guide, guide2);
        List<GuideDto> guidesDto = List.of(guideDto, guideDto2);
        RequestBuilder requestBuilder = get("/guides/");

        given(guideService.getAllGuides()).willReturn(guides);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(guidesDto));
    }

    @Test
    public void shouldThrowGuideNotFoundExceptionWhenValidIdNotExistsInGetMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = get("/guides/" + id);

        given(guideService.getGuide(id)).willThrow(GuideNotFoundException.class);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnStatusCreatedInAddMethod() throws Exception {
        //given
        RequestBuilder requestBuilder = post("/guides/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guideDto));

        given(guideService.addGuide(any(Guide.class))).willReturn(guide);


        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(guideDto));
    }

    @Test
    public void shouldReturnNoContentStatusWhenUpdatedProperly() throws Exception {
        //given
        long id = 1L;
        RequestBuilder requestBuilder = put("/guides/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guideDto));

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        verify(guideService).updateGuide(guide);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeletedProperly() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/guides/" + id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        verify(guideService).deleteGuide(id);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowGuideNotFoundExceptionWhenValidIdNotExistsInDeleteMethod() throws Exception {
        //given
        long id = 1L;
        RequestBuilder requestBuilder = delete("/guides/" + id);

        doThrow(new GuideNotFoundException(id)).when(guideService).deleteGuide(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
