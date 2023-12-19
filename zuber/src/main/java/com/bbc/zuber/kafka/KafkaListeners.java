package com.bbc.zuber.kafka;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.service.RideAssignmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

    private final RideAssignmentService rideAssignmentService;
    private final ObjectMapper objectMapper;


    @KafkaListener(topics = "ride-assignment")
    void userRegistrationListener(String rideAssignmentJson) throws JsonProcessingException {
        rideAssignmentService.save(objectMapper.readValue(rideAssignmentJson, RideAssignment.class));
        logger.info("Successfully saved rideAssignment from zuber_server");
    }

}
