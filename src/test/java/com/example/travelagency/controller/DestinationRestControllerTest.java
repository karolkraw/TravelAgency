package com.example.travelagency.controller;


import com.example.travelagency.destination.Destination;
import com.example.travelagency.destination.DestinationRestController;
import com.example.travelagency.destination.DestinationService;
import com.example.travelagency.destination.dto.DestinationDto;
import com.example.travelagency.exception.DestinationNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class DestinationRestControllerTest {
    @Mock
    private DestinationService destinationService;

    @InjectMocks
    private DestinationRestController destinationRestController;

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(destinationRestController).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void shouldReturnDestinationDtoWhenValidIdExists() throws Exception {
        //given
        Long id = 1L;
        Destination destination = new Destination(id, "Paris");
        DestinationDto destinationDto = new DestinationDto(id, "Paris");
        RequestBuilder requestBuilder = get("/destinations/" + id);

        given(destinationService.getDestination(id)).willReturn(destination);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(destinationDto));
    }

    @Test
    public void shouldThrowDestinationNotFoundExceptionWhenValidIdNotExistsInGetMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = get("/destinations/" + id);

        given(destinationService.getDestination(id)).willThrow(DestinationNotFoundException.class);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnAllDestinationsAndStatusOk() throws Exception {
        //given
        List<Destination> destinations = List.of(
                new Destination(1L, "Paris"),
                new Destination(2L, "London"),
                new Destination(3L, "Madrid"));
        List<DestinationDto> destinationsDto = List.of(
                new DestinationDto(1L, "Paris"),
                new DestinationDto(2L, "London"),
                new DestinationDto(3L, "Madrid"));

        RequestBuilder requestBuilder = get("/destinations/");
        given(destinationService.getAllDestinations()).willReturn(destinations);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(destinationsDto));
    }

    @Test
    public void shouldAddDestinationAndReturnStatusCreated() throws Exception {
        //given
        Long id = 1L;
        Destination destination = new Destination(1L, "Paris");
        DestinationDto destinationDto = new DestinationDto(id, "Paris");

        RequestBuilder requestBuilder = post("/destinations/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destinationDto));

        given(destinationService.addDestination(any(Destination.class))).willReturn(destination);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(destination));
    }

    @Test
    public void shouldReturnNoContentStatusWhenDeletedProperly() throws Exception {
        //given
        long id = 1L;
        RequestBuilder requestBuilder = delete("/destinations/" + id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        verify(destinationService).deleteDestination(id);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowDestinationNotFoundExceptionWhenValidIdNotExistsInDeleteMethod() throws Exception {
        //given
        long id = 1L;
        RequestBuilder requestBuilder = delete("/destinations/" + id);

        doThrow(new DestinationNotFoundException(id)).when(destinationService).deleteDestination(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnNoContentStatusWhenUpdatedProperly() throws Exception {
        //given
        Long id = 1L;
        Destination destination = new Destination(1L, "Paris");
        DestinationDto destinationDto = new DestinationDto(id, "Paris");

        RequestBuilder requestBuilder = put("/destinations/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destinationDto));

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        verify(destinationService).updateDestination(destination);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowDestinationNotFoundExceptionWhenValidIdNotExistsInUpdateMethod() throws Exception {
        //given
        Long id = 1L;
        Destination destination = new Destination(id, "Paris");
        DestinationDto destinationDto = new DestinationDto(id, "Paris");

        RequestBuilder requestBuilder = put("/destinations/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destinationDto));

        doThrow(DestinationNotFoundException.class).when(destinationService).updateDestination(destination);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}


