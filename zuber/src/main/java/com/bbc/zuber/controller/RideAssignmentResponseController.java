package com.bbc.zuber.controller;

import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import com.bbc.zuber.service.RideAssignmentResponseService;
import com.bbc.zuber.service.RideAssignmentService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rideAssignmentResponse")
@RequiredArgsConstructor
public class RideAssignmentResponseController {
    private final RideAssignmentResponseService rideAssignmentResponseService;
    private final RideAssignmentService rideAssignmentService;

    @PostMapping
    public ResponseEntity<String>  rideAssignmentResponse(@RequestBody RideAssignmentResponse command){
        rideAssignmentResponseService.save(command);
        rideAssignmentService.updateStatus(command.getId(), command.getAccepted());
        return ResponseEntity.ok("Successfully update status");
    }

}
