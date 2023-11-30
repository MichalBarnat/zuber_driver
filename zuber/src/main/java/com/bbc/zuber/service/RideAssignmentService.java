package com.bbc.zuber.service;

import com.bbc.zuber.model.rideassignment.RideAssignment;
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

}
