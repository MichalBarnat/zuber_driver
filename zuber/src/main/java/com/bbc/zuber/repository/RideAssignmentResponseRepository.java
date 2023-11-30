package com.bbc.zuber.repository;

import com.bbc.zuber.model.rideassignmentresponse.RideAssignmentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideAssignmentResponseRepository extends JpaRepository<RideAssignmentResponse, Long> {
}
