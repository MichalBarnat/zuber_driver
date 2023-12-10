package com.bbc.zuber.model.car.command;

import com.bbc.zuber.model.car.enums.TypeOfCar;
import com.bbc.zuber.model.car.enums.TypeOfEngine;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CarDataCommand {
    private String brand;
    private String model;
    private int productionYear;
    @Enumerated(EnumType.STRING)
    private TypeOfEngine engine;
    @Enumerated(EnumType.STRING)
    private TypeOfCar type;
    private int size;
    private String plateNum;
}
