package com.bbc.zuber.model.rideassignmentresponse;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ride_assignment_response_seq")
    @SequenceGenerator(name = "ride_assignment_response_seq", sequenceName = "ride_assignment_response_seq", allocationSize = 1)
    private Long id;
    private Boolean accepted;
}