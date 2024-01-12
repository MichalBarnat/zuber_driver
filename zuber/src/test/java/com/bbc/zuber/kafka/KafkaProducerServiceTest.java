package com.bbc.zuber.kafka;

import com.bbc.zuber.exception.KafkaSerializationException;
import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.UUID;

import static com.bbc.zuber.model.driver.enums.Sex.MALE;
import static com.bbc.zuber.model.driver.enums.StatusDriver.AVAILABLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class KafkaProducerServiceTest {

    private Driver driver;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;
    @InjectMocks
    private KafkaProducerService producerService;

    @BeforeEach
    void setUp() {
        openMocks(this);

        driver = new Driver(1L, UUID.randomUUID(), "Test", "Test",
                LocalDate.of(2000,1,1), AVAILABLE,
                MALE, "test.t@example.com", "Test", new Car(), false);
    }

    @Test
    void shouldSendDriverRegistrationToKafka() throws JsonProcessingException {
        //Given
        String expectedJson = "{" +
                "\"id\":" + driver.getId() + "," +
                "\"uuid\":" + driver.getUuid() + "\"," +
                "\"name\":" + driver.getName() + "\"," +
                "\"surname\":" + driver.getSurname() + "\"," +
                "\"dob\":" + driver.getDob() + "\"," +
                "\"statusDriver\":" + driver.getStatusDriver() + "\"," +
                "\"sex\":" + driver.getSex() + "\"," +
                "\"email\":" + driver.getEmail() + "\"," +
                "\"location\":" + driver.getLocation() + "\"," +
                "\"car\":" + driver.getCar() + "\"," +
                "}";

        when(objectMapper.writeValueAsString(driver))
                .thenReturn(expectedJson);

        //When
        producerService.sendDriverRegistration(driver);

        //Then
        verify(objectMapper, times(1)).writeValueAsString(driver);
        verify(kafkaTemplate, times(1)).send(eq("driver-registration"), eq(expectedJson));
    }

    @Test
    void shouldSendDriverEditedToKafka() throws JsonProcessingException {
        //Given
        String expectedJson = "{" +
                "\"id\":" + driver.getId() + "," +
                "\"uuid\":" + driver.getUuid() + "\"," +
                "\"name\":" + driver.getName() + "\"," +
                "\"surname\":" + driver.getSurname() + "\"," +
                "\"dob\":" + driver.getDob() + "\"," +
                "\"statusDriver\":" + driver.getStatusDriver() + "\"," +
                "\"sex\":" + driver.getSex() + "\"," +
                "\"email\":" + driver.getEmail() + "\"," +
                "\"location\":" + driver.getLocation() + "\"," +
                "\"car\":" + driver.getCar() + "\"," +
                "}";

        when(objectMapper.writeValueAsString(driver))
                .thenReturn(expectedJson);

        //When
        producerService.sendDriverEdited(driver);

        //Then
        verify(objectMapper, times(1)).writeValueAsString(driver);
        verify(kafkaTemplate, times(1)).send(eq("driver-edited"), eq(expectedJson));

    }

    @Test
    void shouldSendRideAssignmentResponseToKafka() throws JsonProcessingException {
        //Given
        RideAssignmentResponse response = RideAssignmentResponse.builder()
                .id(1L)
                .rideAssignmentId(1L)
                .accepted(true)
                .build();

        String expectedJson = "{" +
                "\"id\":" + response.getId() + "," +
                "\"rideAssignmentId\":" + response.getRideAssignmentId() + "\"," +
                "\"accepted\":" + response.getAccepted() + "\"," +
                "}";

        when(objectMapper.writeValueAsString(response))
                .thenReturn(expectedJson);

        //When
        producerService.sendRideAssignmentResponse(response);

        //Then
        verify(objectMapper, times(1)).writeValueAsString(response);
        verify(kafkaTemplate, times(1)).send(eq("ride-assignment-response"), eq(expectedJson));
    }

    @Test
    void shouldThrowKafkaSerializationExceptionForSendDriverRegistrationMethod() throws JsonProcessingException {
        //Given
        when(objectMapper.writeValueAsString(driver))
                .thenThrow(JsonProcessingException.class);

        //When & Then
        assertThrows(KafkaSerializationException.class, () -> producerService.sendDriverRegistration(driver));

        verify(objectMapper, times(1)).writeValueAsString(driver);
        verify(kafkaTemplate, never()).send(eq("driver-registration"), anyString());
    }

    @Test
    void shouldThrowKafkaSerializationExceptionForSendDriverEditedMethod() throws JsonProcessingException {
        //Given
        when(objectMapper.writeValueAsString(driver))
                .thenThrow(JsonProcessingException.class);

        //When & Then
        assertThrows(KafkaSerializationException.class, () -> producerService.sendDriverEdited(driver));

        verify(objectMapper, times(1)).writeValueAsString(driver);
        verify(kafkaTemplate, never()).send(eq("driver-edited"), anyString());
    }

    @Test
    void shouldThrowKafkaSerializationExceptionForSendRideAssignmentResponseMethod() throws JsonProcessingException {
        //Given
        RideAssignmentResponse response = RideAssignmentResponse.builder()
                .id(1L)
                .rideAssignmentId(1L)
                .accepted(true)
                .build();

        when(objectMapper.writeValueAsString(response))
                .thenThrow(JsonProcessingException.class);

        //When & Then
        assertThrows(KafkaSerializationException.class, () -> producerService.sendRideAssignmentResponse(response));

        verify(objectMapper, times(1)).writeValueAsString(response);
        verify(kafkaTemplate, never()).send(eq("ride-assignment-response"), anyString());
    }
}