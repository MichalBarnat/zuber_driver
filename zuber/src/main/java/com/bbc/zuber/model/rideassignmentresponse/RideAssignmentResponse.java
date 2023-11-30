package com.bbc.zuber.model.rideassignmentresponse;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ride_assignment_response")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideAssignmentResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Boolean accepted;
}
