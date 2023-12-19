package com.bbc.zuber.kafka;

import com.bbc.zuber.exception.KafkaSerializationException;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendDriverRegistration(Driver savedDriver) {
        try {
            String savedDriverJson = objectMapper.writeValueAsString(savedDriver);
            kafkaTemplate.send("driver-registration", savedDriverJson);
        } catch (JsonProcessingException e) {
            throw new KafkaSerializationException();
        }
    }

    public void sendDriverEdited(Driver editedDriver) {
        try {
            String editedDriverJson = objectMapper.writeValueAsString(editedDriver);
            kafkaTemplate.send("driver-edited", editedDriverJson);
        } catch (JsonProcessingException e) {
            throw new KafkaSerializationException();
        }
    }

    public void sendRideAssignmentResponse(RideAssignmentResponse response) {
        try {
            String responseJson = objectMapper.writeValueAsString(response);
            kafkaTemplate.send("ride-assignment-response", responseJson);
        } catch (JsonProcessingException e) {
            throw new KafkaSerializationException();
        }
    }
}
