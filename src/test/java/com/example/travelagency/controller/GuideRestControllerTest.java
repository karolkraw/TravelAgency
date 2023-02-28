package com.example.travelagency.controller;

import com.example.travelagency.controller.dto.destination.DestinationDto;
import com.example.travelagency.controller.dto.guide.GuideDto;
import com.example.travelagency.controller.dto.trip.TripReadDto;
import com.example.travelagency.exception.GuideNotFoundException;
import com.example.travelagency.model.Destination;
import com.example.travelagency.model.Guide;
import com.example.travelagency.model.Trip;
import com.example.travelagency.service.GuideService;
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

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = {"ADMIN"})
public class GuideRestControllerTest {
    @MockBean
    private GuideService guideService;

    @Autowired
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
                .destinationDto(new DestinationDto(1L, "Paris")).build();

        trip2 = Trip.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destination(new Destination(1L, "London")).build();
        tripReadDto2 = TripReadDto.builder()
                .id(6L).price(BigDecimal.valueOf(750L))
                .departureDate(LocalDate.now().plusDays(5))
                .returnDate(LocalDate.now().plusDays(15))
                .destinationDto(new DestinationDto(1L, "London")).build();

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

        RequestBuilder requestBuilder = get("/guides/get/" + id);

        given(guideService.getGuide(id)).willReturn(guide);

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
        RequestBuilder requestBuilder = get("/guides/get/");
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
        RequestBuilder requestBuilder = get("/guides/get/" + id);

        given(guideService.getGuide(id)).willThrow(GuideNotFoundException.class);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnStatusCreatedInAddMethod() throws Exception {
        //given
        Guide guideWithNullId = new Guide(null, "John", "Miller", List.of(trip, trip2));
        RequestBuilder requestBuilder = post("/guides/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guideDto));

        given(guideService.addGuide(guideWithNullId)).willReturn(guide);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/guides/get/{id}") // change it to get value from properties
                .buildAndExpand(guide.getId());

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
        RequestBuilder requestBuilder = put("/guides/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(guideDto));

        doNothing().when(guideService).deleteGuide(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeletedProperly() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/guides/delete/" + id);

        doNothing().when(guideService).deleteGuide(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowGuideNotFoundExceptionWhenValidIdNotExistsInDeleteMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/guide/delete/" + id);

        doThrow(GuideNotFoundException.class).when(guideService).deleteGuide(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
