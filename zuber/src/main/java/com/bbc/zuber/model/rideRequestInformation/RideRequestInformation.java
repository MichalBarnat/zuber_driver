package com.bbc.zuber.model.rideRequestInformation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "rideRequestsInformation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequestInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    private String pickUpLocation;
    private String dropOffLocation;
    // Może imię pasażera
    // Musi być kwota za kurs do otrzymania
}
