package com.bbc.zuber.model.rideassignment;

import com.bbc.zuber.model.rideassignment.enums.RideAssignmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "ride_assignments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID uuid;
    private UUID rideRequestUUID;
    private UUID driverUUID;
    private String pickUpLocation;
    private String dropOffLocation;
    @Enumerated(EnumType.STRING)
    private RideAssignmentStatus status;
}