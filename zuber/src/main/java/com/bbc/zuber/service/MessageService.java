package com.bbc.zuber.service;

import com.bbc.zuber.exception.DriverUuidNotFoundException;
import com.bbc.zuber.feign.ServerFeignClient;
import com.bbc.zuber.kafka.KafkaProducerService;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.message.command.CreateMessageCommand;
import com.bbc.zuber.model.message.dto.MessageDto;
import com.bbc.zuber.model.message.response.MessageResponse;
import com.bbc.zuber.model.rideInfo.RideInfo;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import com.bbc.zuber.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

import static com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus.ACCEPTED;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final DriverRepository driverRepository;
    private final KafkaProducerService producerService;
    private final ServerFeignClient serverFeignClient;

    public MessageResponse sendMessage(CreateMessageCommand command) {
        Driver driver = driverRepository.findByUuid(command.getSenderUuid())
                .orElseThrow(() -> new DriverUuidNotFoundException(command.getSenderUuid()));

        RideInfo rideInfo = serverFeignClient.findByDriverUuid(driver.getUuid());

        RideAssignment rideAssignment = serverFeignClient.findByUuid(rideInfo.getRideAssignmentUuid());

        if (rideAssignment.getStatus() != ACCEPTED) {
            return MessageResponse.builder()
                    .message("Your ride is not accepted. Cannot send message.")
                    .build();
        }

//        if (rideAssignment.getStatus() == COMPLETED) {
//            return MessageResponse.builder()
//                    .message("Your ride has been completed. Cannot send messages.")
//                    .build();
//        }

        producerService.sendMessage(command);

        return MessageResponse.builder()
                .message("Message sent successfully.")
                .build();
    }

    public LinkedList<MessageDto> getMessages(long rideInfoId) {
        return serverFeignClient.findMessagesByRideInfoId(rideInfoId);
    }

}
