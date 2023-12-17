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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ride_assignments_seq")
    @SequenceGenerator(name = "ride_assignments_seq", sequenceName = "ride_assignments_seq", allocationSize = 1)
    private Long id;
    private UUID uuid;
    @Column(name = "ride_request_uuid")
    private UUID rideRequestUUID;
    @Column(name = "driver_uuid")
    private UUID driverUUID;
    @Column(name = "pick_up_location")
    private String pickUpLocation;
    @Column(name = "drop_off_location")
    private String dropOffLocation;
    @Enumerated(EnumType.STRING)
    private RideAssignmentStatus status;
}