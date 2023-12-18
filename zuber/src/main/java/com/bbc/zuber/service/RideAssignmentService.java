package com.bbc.zuber.service;

import com.bbc.zuber.exception.RideAssignmentNotFoundException;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.repository.RideAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus.ACCEPTED;
import static com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus.REJECTED;

@Service
@RequiredArgsConstructor
public class RideAssignmentService {

    private final RideAssignmentRepository rideAssignmentRepository;

    @Transactional
    public RideAssignment save(RideAssignment rideAssignment) {
        return rideAssignmentRepository.save(rideAssignment);
    }

    @Transactional(readOnly = true)
    public RideAssignment findById(Long id) {
        return rideAssignmentRepository.findById(id)
                .orElseThrow(() -> new RideAssignmentNotFoundException(id));
    }

    @Transactional
    public RideAssignment updateStatus(Long id, boolean accepted) {
        RideAssignment rideAssignment = findById(id);
        if (accepted) {
            rideAssignment.setStatus(ACCEPTED);
        } else {
            rideAssignment.setStatus(REJECTED);
        }
        return save(rideAssignment);
    }
}
