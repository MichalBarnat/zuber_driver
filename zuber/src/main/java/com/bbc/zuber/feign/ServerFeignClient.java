package com.bbc.zuber.feign;

import com.bbc.zuber.model.message.dto.MessageDto;
import com.bbc.zuber.model.rideInfo.RideInfo;
import com.bbc.zuber.model.rideassignment.RideAssignment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.LinkedList;
import java.util.UUID;

@FeignClient(name = "server-service", url = "http://localhost:8090")
public interface ServerFeignClient {

    @GetMapping("/api/conversation/{rideInfoId}")
    LinkedList<MessageDto> findMessagesByRideInfoId(@PathVariable long rideInfoId);

    @GetMapping("/api/ride-info/driver/{driverUuid}")
    RideInfo findByDriverUuid(@PathVariable UUID driverUuid);

    @GetMapping("/api/ride-assignment/{uuid}")
    RideAssignment findByUuid(@PathVariable UUID uuid);
}
