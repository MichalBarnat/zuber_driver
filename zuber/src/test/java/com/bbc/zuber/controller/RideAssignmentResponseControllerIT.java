package com.bbc.zuber.controller;

import com.bbc.zuber.DatabaseCleaner;
import com.bbc.zuber.DriverAppApplication;
import com.bbc.zuber.model.rideassignmentresponse.command.CreateRideAssignmentResponseCommand;
import com.bbc.zuber.service.RideAssignmentResponseService;
import com.bbc.zuber.service.RideAssignmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DriverAppApplication.class)
@AutoConfigureMockMvc
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        },
        controlledShutdown = true,
        brokerPropertiesLocation = "classpath:embedded-kafka-broker-test.yml"
)
@DirtiesContext
@ActiveProfiles("test")
class RideAssignmentResponseControllerIT {

    private final MockMvc postman;
    private final ObjectMapper objectMapper;
    private final DatabaseCleaner databaseCleaner;

    @MockBean
    private RideAssignmentService rideAssignmentService;

    @MockBean
    private RideAssignmentResponseService rideAssignmentResponseService;

    @Autowired
    public RideAssignmentResponseControllerIT(MockMvc postman, ObjectMapper objectMapper, DatabaseCleaner databaseCleaner) {
        this.postman = postman;
        this.objectMapper = objectMapper;
        this.databaseCleaner = databaseCleaner;
    }

    @Test
    void shouldReturnBadRequestWhenRideAssignmentIdDoesNotExistsForRideAssignmentResponseMethod() throws Exception {
        //Given
        long id = 1L;
        CreateRideAssignmentResponseCommand command = CreateRideAssignmentResponseCommand.builder()
                .rideAssignmentId(1L)
                .accepted(false)
                .build();

        when(rideAssignmentService.existById(id))
                .thenReturn(false);

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(post("/rideAssignmentResponse")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Wrong RideAssignment id!"));

        //Then
        verify(rideAssignmentService, times(1)).existById(id);
    }
}