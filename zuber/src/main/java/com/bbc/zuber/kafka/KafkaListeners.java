package com.bbc.zuber.kafka;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.service.RideAssignmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final RideAssignmentService rideAssignmentService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "ride-assignment")
    void userRegistrationListener(String rideAssignmentJson) throws JsonProcessingException {
        rideAssignmentService.save(objectMapper.readValue(rideAssignmentJson, RideAssignment.class));
        System.out.println("Successfully saved rideAssignment from zuber_server");

        //todo dodac logger
    }

}
