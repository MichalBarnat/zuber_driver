package com.bbc.zuber.service;

import com.bbc.zuber.kafka.KafkaProducerService;
import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.repository.RideAssignmentResponseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class RideAssignmentResponseServiceTest {

    @Mock
    private RideAssignmentResponseRepository rideAssignmentResponseRepository;
    @Mock
    private KafkaProducerService producerService;
    @InjectMocks
    private RideAssignmentResponseService rideAssignmentResponseService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldSaveRideAssignmentResponse() {
        //Given
        RideAssignmentResponse response = RideAssignmentResponse.builder()
                .id(1L)
                .rideAssignmentId(1L)
                .accepted(true)
                .build();

        when(rideAssignmentResponseRepository.save(any(RideAssignmentResponse.class)))
                .thenReturn(response);

        //When
        RideAssignmentResponse result = rideAssignmentResponseService.save(response);

        //Then
        verify(rideAssignmentResponseRepository, times(1)).save(any(RideAssignmentResponse.class));
        verify(producerService, times(1)).sendRideAssignmentResponse(response);

        assertEquals(response, result);
    }
}