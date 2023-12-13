package com.bbc.zuber.service;

import com.bbc.zuber.exception.RideAssignmentNotFoundException;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import com.bbc.zuber.repository.RideAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideAssignmentService {

    private final RideAssignmentRepository rideAssignmentRepository;

    public RideAssignment save(RideAssignment rideAssignment) {
        return rideAssignmentRepository.save(rideAssignment);
    }

    public RideAssignment findById(Long id) {
        return rideAssignmentRepository.findById(id).orElseThrow(
                () -> new RideAssignmentNotFoundException(String.format("RideAssignment with id %d not found!", id)));
    }

    public RideAssignment updateStatus(Long id, boolean accepted) {
        RideAssignment rideAssignment = findById(id);
        if (accepted) {
            rideAssignment.setStatus(RideAssignmentStatus.ACCEPTED);
        } else {
            rideAssignment.setStatus(RideAssignmentStatus.REJECTED);
        }
        return save(rideAssignment);
    }
}
