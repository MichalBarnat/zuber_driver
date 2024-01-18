package com.bbc.zuber.model.rideInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideInfo {

    private Long id;
    private UUID rideAssignmentUuid;
    private UUID userUuid;
    private UUID driverUuid;
    private String driverName;
    private String driverLocation;
    private String pickUpLocation;
    private String dropUpLocation;
    private LocalDateTime orderTime;
    private LocalDateTime estimatedArrivalTime;
    private BigDecimal costOfRide;
    private String timeToArrivalInMinutes;
    private String rideLengthInKilometers;
}
