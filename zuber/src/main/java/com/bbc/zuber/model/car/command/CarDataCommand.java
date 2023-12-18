package com.bbc.zuber.model.car.command;

import com.bbc.zuber.model.car.enums.TypeOfCar;
import com.bbc.zuber.model.car.enums.TypeOfEngine;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarDataCommand {

    private String brand;
    private String model;
    private int productionYear;
    @Enumerated(STRING)
    private TypeOfEngine engine;
    @Enumerated(STRING)
    private TypeOfCar type;
    private int size;
    private String plateNum;
}
