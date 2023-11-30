package com.bbc.zuber.kafka;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.service.RideAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final RideAssignmentService rideAssignmentService;

    @KafkaListener(topics = "ride-assignment")
    void userRegistrationListener(RideAssignment rideAssignment) {
        rideAssignmentService.save(rideAssignment);
        System.out.println("Successfully saved rideAssignment from zuber_server");
    }

}
