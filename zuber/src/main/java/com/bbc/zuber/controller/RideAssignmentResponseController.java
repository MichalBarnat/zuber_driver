package com.bbc.zuber.controller;

import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.service.RideAssignmentResponseService;
import com.bbc.zuber.service.RideAssignmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rideAssignmentResponse")
@RequiredArgsConstructor
public class RideAssignmentResponseController {
    private final RideAssignmentResponseService rideAssignmentResponseService;
    private final RideAssignmentService rideAssignmentService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    public ResponseEntity<String>  rideAssignmentResponse(@RequestBody RideAssignmentResponse command) throws JsonProcessingException {
        rideAssignmentResponseService.save(command);
        rideAssignmentService.updateStatus(command.getId(), command.getAccepted());
        String commandJson = objectMapper.writeValueAsString(command);
        kafkaTemplate.send("ride-assignment-response", commandJson);
        return ResponseEntity.ok("Successfully update status");
    }

}
