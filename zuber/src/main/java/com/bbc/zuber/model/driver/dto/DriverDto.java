package com.bbc.zuber.model.driver.dto;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.car.dto.CarDto;
import com.bbc.zuber.model.driver.enums.Sex;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class DriverDto {
    Long id;
    UUID uuid;
    String name;
    Sex sex;
    String location;
    CarDto carDto;
}
