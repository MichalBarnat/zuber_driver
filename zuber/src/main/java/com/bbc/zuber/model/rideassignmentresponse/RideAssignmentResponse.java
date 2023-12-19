package com.bbc.zuber.model.rideassignmentresponse;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ride_assignments_responses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideAssignmentResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ride_assignments_responses_seq")
    @SequenceGenerator(name = "ride_assignments_responses_seq", sequenceName = "ride_assignments_responses_seq", allocationSize = 1)
    private Long id;
    @Column(name = "ride_assignment_id")
    private Long rideAssignmentId;
    private Boolean accepted;
}