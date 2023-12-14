package com.bbc.zuber.model.driver.command;

import com.bbc.zuber.model.car.command.CarDataCommand;
import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateDriverCommand {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String dob;
    @Enumerated(EnumType.STRING)
    private StatusDriver statusDriver;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Email
    private String email;
    @NotNull
    private String location;
    private CarDataCommand carData;
}
