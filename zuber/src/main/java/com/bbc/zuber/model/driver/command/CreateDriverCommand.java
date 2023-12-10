package com.bbc.zuber.model.driver.command;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.car.command.CarDataCommand;
import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateDriverCommand {
    private String name;
    private String surname;
    private String dob;
    private StatusDriver statusDriver;
    private Sex sex;
    private String email;
    private String location;
    private Car car;
    private CarDataCommand carData;
}
