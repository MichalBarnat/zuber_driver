package com.bbc.zuber.model.car;

import com.bbc.zuber.model.car.enums.TypeOfCar;
import com.bbc.zuber.model.car.enums.TypeOfEngine;
import com.bbc.zuber.model.driver.Driver;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cars_seq")
    @SequenceGenerator(name = "cars_seq", sequenceName = "cars_seq", allocationSize = 1)
    private Long id;
    private UUID uuid;
    private String brand;
    private String model;
    private int productionYear;
    @Enumerated(EnumType.STRING)
    private TypeOfEngine engine;
    @Enumerated(EnumType.STRING)
    private TypeOfCar type;
    private int size;
    private String plateNum;
    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    @JoinColumn(name = "DRIVER_UUID", referencedColumnName = "uuid")
    @JsonIgnore
    private Driver driver;
}
