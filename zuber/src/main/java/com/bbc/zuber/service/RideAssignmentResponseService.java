package com.bbc.zuber.service;

import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.repository.RideAssignmentResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideAssignmentResponseService {
    private final RideAssignmentResponseRepository rideAssignmentResponseRepository;

    public RideAssignmentResponse save(RideAssignmentResponse rideAssignmentResponse){
        return rideAssignmentResponseRepository.save(rideAssignmentResponse);
    }
}
