package com.bbc.zuber.controller;

import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.model.rideassignmentresponse.command.CreateRideAssignmentResponseCommand;
import com.bbc.zuber.model.rideassignmentresponse.response.RideAssignmentUpdateStatusResponse;
import com.bbc.zuber.service.RideAssignmentResponseService;
import com.bbc.zuber.service.RideAssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/rideAssignmentResponse")
@RequiredArgsConstructor
public class RideAssignmentResponseController {

    private final RideAssignmentService rideAssignmentService;
    private final RideAssignmentResponseService rideAssignmentResponseService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<RideAssignmentUpdateStatusResponse> rideAssignmentResponse(@RequestBody @Valid CreateRideAssignmentResponseCommand command) {
        RideAssignmentResponse rideAssignmentResponse = modelMapper.map(command, RideAssignmentResponse.class);
        rideAssignmentResponseService.save(rideAssignmentResponse);
        RideAssignmentUpdateStatusResponse response = rideAssignmentService.updateStatus(command.getRideAssignmentId(), command.getAccepted());
        return new ResponseEntity<>(response, OK);
    }
}
