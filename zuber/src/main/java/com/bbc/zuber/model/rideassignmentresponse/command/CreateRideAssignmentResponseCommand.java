package com.bbc.zuber.model.rideassignmentresponse.command;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateRideAssignmentResponseCommand {

    private Long rideAssignmentId;
    private Boolean accepted;
}
