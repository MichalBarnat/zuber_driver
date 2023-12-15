package com.bbc.zuber.service;

import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.repository.RideAssignmentResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RideAssignmentResponseService {

    private final RideAssignmentResponseRepository rideAssignmentResponseRepository;

    @Transactional
    public RideAssignmentResponse save(RideAssignmentResponse rideAssignmentResponse) {
        return rideAssignmentResponseRepository.save(rideAssignmentResponse);
    }
}
