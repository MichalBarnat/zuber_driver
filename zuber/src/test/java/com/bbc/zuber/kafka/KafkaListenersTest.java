package com.bbc.zuber.kafka;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import com.bbc.zuber.service.RideAssignmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus.PENDING;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class KafkaListenersTest {

    @Mock
    private RideAssignmentService rideAssignmentService;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private KafkaListeners kafkaListeners;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldConsumeUserRegistrationListenerFromKafka() throws JsonProcessingException {
        //Given
        RideAssignment rideAssignment = RideAssignment.builder()
                .id(1L)
                .uuid(UUID.randomUUID())
                .rideRequestUUID(UUID.randomUUID())
                .driverUUID(UUID.randomUUID())
                .pickUpLocation("Test")
                .dropOffLocation("Test")
                .status(PENDING)
                .build();

        String rideAssignmentString = objectMapper.writeValueAsString(rideAssignment);

        when(objectMapper.readValue(rideAssignmentString, RideAssignment.class))
                .thenReturn(rideAssignment);

        //When
        kafkaListeners.userRegistrationListener(rideAssignmentString);

        //Then
        verify(objectMapper, times(1)).readValue(rideAssignmentString, RideAssignment.class);
        verify(rideAssignmentService, times(1)).save(rideAssignment);
    }
}