package com.bbc.zuber.service;

import com.bbc.zuber.exception.RideAssignmentNotFoundException;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignmentresponse.response.RideAssignmentUpdateStatusResponse;
import com.bbc.zuber.repository.RideAssignmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus.ACCEPTED;
import static com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus.CANCELLED;
import static com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus.REJECTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class RideAssignmentServiceTest {

    private RideAssignment rideAssignment;
    @Mock
    private RideAssignmentRepository rideAssignmentRepository;
    @InjectMocks
    private RideAssignmentService rideAssignmentService;

    @BeforeEach
    void setUp() {
        openMocks(this);

        rideAssignment = new RideAssignment(1L, UUID.randomUUID(), UUID.randomUUID(),
                UUID.randomUUID(), "Test", "Test", ACCEPTED);
    }

    @Test
    void shouldSaveRideAssignment() {
        //Given
        when(rideAssignmentRepository.save(any(RideAssignment.class)))
                .thenReturn(rideAssignment);

        //When
        RideAssignment result = rideAssignmentService.save(rideAssignment);

        //Then
        verify(rideAssignmentRepository, times(1)).save(any(RideAssignment.class));

        assertEquals(rideAssignment, result);
    }

    @Test
    void shouldFindByRideAssignmentId() {
        //Given
        long rideAssignmentId = 1L;

        when(rideAssignmentRepository.findById(rideAssignmentId))
                .thenReturn(Optional.of(rideAssignment));

        //When
        RideAssignment result = rideAssignmentService.findById(rideAssignmentId);

        //Then
        verify(rideAssignmentRepository, times(1)).findById(rideAssignmentId);

        assertEquals(rideAssignment, result);
    }

    @Test
    void shouldUpdateStatusForRideAssignmentWhenStatusIsCancelled() {
        //Given
        long rideAssignmentId = 1L;
        rideAssignment.setStatus(CANCELLED);

        when(rideAssignmentRepository.findById(rideAssignmentId))
                .thenReturn(Optional.of(rideAssignment));

        //When
        RideAssignmentUpdateStatusResponse result = rideAssignmentService.updateStatus(rideAssignmentId, true);

        //Then
        verify(rideAssignmentRepository, times(1)).findById(rideAssignmentId);
        verify(rideAssignmentRepository, never()).save(any(RideAssignment.class));

        assertEquals("Can't do nothing because Ride was CANCELLED!", result.getMessage());
    }

    @Test
    void shouldUpdateStatusForRideAssignmentWhenStatusIsAccepted() {
        //Given
        long rideAssignmentId = 1L;

        when(rideAssignmentRepository.findById(rideAssignmentId))
                .thenReturn(Optional.of(rideAssignment));
        when(rideAssignmentRepository.save(any(RideAssignment.class)))
                .thenReturn(rideAssignment);

        //When
        RideAssignmentUpdateStatusResponse result = rideAssignmentService.updateStatus(rideAssignmentId, true);

        //Then
        verify(rideAssignmentRepository, times(1)).findById(rideAssignmentId);
        verify(rideAssignmentRepository, times(1)).save(any(RideAssignment.class));

        assertEquals(ACCEPTED, rideAssignment.getStatus());
        assertEquals("Successfully update RideAssignment with id: 1", result.getMessage());
    }

    @Test
    void shouldUpdateStatusForRideAssignmentWhenStatusIsRejected() {
        //Given
        long rideAssignmentId = 1L;

        when(rideAssignmentRepository.findById(rideAssignmentId))
                .thenReturn(Optional.of(rideAssignment));
        when(rideAssignmentRepository.save(any(RideAssignment.class)))
                .thenReturn(rideAssignment);

        //When
        RideAssignmentUpdateStatusResponse result = rideAssignmentService.updateStatus(rideAssignmentId, false);

        //Then
        verify(rideAssignmentRepository, times(1)).findById(rideAssignmentId);
        verify(rideAssignmentRepository, times(1)).save(any(RideAssignment.class));

        assertEquals(REJECTED, rideAssignment.getStatus());
        assertEquals("Successfully update RideAssignment with id: 1", result.getMessage());
    }

    @Test
    void shouldReturnTrueWhenRideAssignmentExistById() {
        //Given
        long rideAssignmentId = 1L;

        when(rideAssignmentRepository.existsById(rideAssignmentId))
                .thenReturn(true);

        //When
        boolean result = rideAssignmentService.existById(rideAssignmentId);

        //Then
        verify(rideAssignmentRepository, times(1)).existsById(rideAssignmentId);

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenRideAssignmentDoesNotExistById() {
        //Given
        long rideAssignmentId = 1L;

        when(rideAssignmentRepository.existsById(rideAssignmentId))
                .thenReturn(false);

        //When
        boolean result = rideAssignmentService.existById(rideAssignmentId);

        //Then
        verify(rideAssignmentRepository, times(1)).existsById(rideAssignmentId);

        assertFalse(result);
    }

    @Test
    void shouldThrowRideAssignmentNotFoundExceptionWhenRideAssignmentIdDoesNotExistsForFindByIdMethod() {
        //Given
        long rideAssignmentId = 1L;

        when(rideAssignmentRepository.findById(rideAssignmentId))
                .thenReturn(Optional.empty());

        //When
        RideAssignmentNotFoundException exception = assertThrows(
                RideAssignmentNotFoundException.class,
                () -> rideAssignmentService.findById(rideAssignmentId)
        );

        //Then
        verify(rideAssignmentRepository, times(1)).findById(rideAssignmentId);

        assertEquals("RideAssignment with id: 1 not found!", exception.getMessage());
    }

    @Test
    void shouldThrowRideAssignmentNotFoundExceptionWhenRideAssignmentIdDoesNotExistsForUpdateStatusMethod() {
        long rideAssignmentId = 1L;

        when(rideAssignmentRepository.findById(rideAssignmentId))
                .thenReturn(Optional.empty());

        //When
        RideAssignmentNotFoundException exception = assertThrows(
                RideAssignmentNotFoundException.class,
                () -> rideAssignmentService.updateStatus(rideAssignmentId, false)
        );

        //Then
        verify(rideAssignmentRepository, times(1)).findById(rideAssignmentId);
        verify(rideAssignmentRepository, never()).save(any(RideAssignment.class));

        assertEquals("RideAssignment with id: 1 not found!", exception.getMessage());
    }
}