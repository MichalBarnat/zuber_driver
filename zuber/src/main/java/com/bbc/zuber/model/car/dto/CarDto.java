package com.bbc.zuber.model.car.dto;

import com.bbc.zuber.model.car.enums.TypeOfCar;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
@AllArgsConstructor
public class CarDto {
    Long id;
    UUID uuid;
    String brand;
    String model;
    int productionYear;
    TypeOfCar type;
    int size;
    String plateNum;
}
