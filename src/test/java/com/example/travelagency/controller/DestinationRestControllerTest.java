package com.example.travelagency.controller;


import com.example.travelagency.controller.dto.destination.DestinationDto;
import com.example.travelagency.exception.DestinationNotFoundException;
import com.example.travelagency.model.Destination;
import com.example.travelagency.service.DestinationService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class DestinationRestControllerTest {
    @MockBean
    private DestinationService destinationService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnDestinationDtoWhenValidIdExists() throws Exception {
        //given
        Long id = 1L;
        Destination destination = new Destination(id, "Paris");
        DestinationDto destinationDto = new DestinationDto(id, "Paris");
        RequestBuilder requestBuilder = get("/destinations/get/" + id);

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
        RequestBuilder requestBuilder = get("/destinations/get/" + id);

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

        RequestBuilder requestBuilder = get("/destinations/get/");
        given(destinationService.getAllDestinations()).willReturn(destinations);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(destinationsDto));
    }

    @Test
    public void shouldAddDestination() throws Exception {
        //given
        Long id = 1L;
        Destination destination = new Destination(null, "Paris");
        DestinationDto destinationDto = new DestinationDto(id, "Paris");

        RequestBuilder requestBuilder = post("/destinations/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destinationDto));

        given(destinationService.addDestination(destination)).willReturn(destination);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/destinations/get/{id}") // change it to get value from properties
                .buildAndExpand(destination.getId());


        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        URI expectedUri = uriComponents.toUri();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(expectedUri.toString()).isEqualTo(response.getHeader("location"));
    }

/*    @Test
    public void shouldReturnStatusCreatedInAddMethod() throws Exception {
        //given
        Long id = 1L;
        Destination destination = new Destination(id, "Paris");
        DestinationDto destinationDto = new DestinationDto(id, "London");

        RequestBuilder requestBuilder = post("/destinations/add/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destinationDto));

        given(destinationService.addDestination(any(Destination.class))).willReturn(destination);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }*/

    @Test
    public void shouldReturnNoContentStatusWhenDeletedProperly() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/destinations/delete/" + id);

        doNothing().when(destinationService).deleteDestination(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowDestinationNotFoundExceptionWhenValidIdNotExistsInDeleteMethod() throws Exception {
        //given
        Long id = 1L;
        RequestBuilder requestBuilder = delete("/destinations/delete/" + id);

        doThrow(DestinationNotFoundException.class).when(destinationService).deleteDestination(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void shouldReturnNoContentStatusWhenUpdatedProperly() throws Exception {
        //given
        Long id = 1L;
        DestinationDto destinationDto = new DestinationDto(id, "Paris");

        RequestBuilder requestBuilder = put("/destinations/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destinationDto));

        doNothing().when(destinationService).deleteDestination(id);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void shouldThrowDestinationNotFoundExceptionWhenValidIdNotExistsInUpdateMethod() throws Exception {
        //given
        Long id = 1L;
        Destination destination = new Destination(id, "Paris");
        DestinationDto destinationDto = new DestinationDto(id, "Paris");

        RequestBuilder requestBuilder = put("/destinations/update/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(destinationDto));

        doThrow(DestinationNotFoundException.class).when(destinationService).updateDestination(destination);

        //when
        MockHttpServletResponse response = mockMvc.perform(requestBuilder).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}


