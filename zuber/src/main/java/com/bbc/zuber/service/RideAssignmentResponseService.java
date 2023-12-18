package com.bbc.zuber.service;

import com.bbc.zuber.kafka.KafkaProducerService;
import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.model.rideassignmentresponse.response.RideAssignmentUpdateStatusResponse;
import com.bbc.zuber.repository.RideAssignmentResponseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RideAssignmentResponseService {

    private final RideAssignmentResponseRepository rideAssignmentResponseRepository;
    private final KafkaProducerService producerService;
    private final RideAssignmentService rideAssignmentService;

    @Transactional
    public RideAssignmentUpdateStatusResponse processAndSave(RideAssignmentResponse rideAssignmentResponse) {
        RideAssignmentResponse response = rideAssignmentResponseRepository.save(rideAssignmentResponse);
        rideAssignmentService.updateStatus(response.getId(), response.getAccepted());

        producerService.sendRideAssignmentResponse(response);

        return RideAssignmentUpdateStatusResponse.builder()
                .message("Successfully update status.")
                .build();
    }
}
