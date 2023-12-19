package com.bbc.zuber.repository;

import com.bbc.zuber.model.rideassignment.RideAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideAssignmentRepository extends JpaRepository<RideAssignment, Long> {
}
