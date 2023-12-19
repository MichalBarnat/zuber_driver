package com.bbc.zuber.model.driver.command;

import com.bbc.zuber.model.car.command.CarDataCommand;
import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import com.bbc.zuber.validations.email.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CreateDriverCommand {
    @NotBlank(message = "NAME_NOT_BLANK")
    private String name;
    @NotBlank(message = "SURNAME_NOT_BLANK")
    private String surname;
    @Past(message = "DOB_CANNOT_BE_FUTURE_OR_PRESENT")
    @NotNull(message = "DOB_NOT_NULL")
    private LocalDate dob;
    @NotNull(message = "STATUS_DRIVER_NOT_NULL")
    private StatusDriver statusDriver;
    @NotNull(message = "SEX_NOT_NULL")
    private Sex sex;
    @UniqueEmail(message = "GIVEN_EMAIL_EXISTS")
    @Email(message = "INCORRECT_EMAIL_FORMAT")
    @NotBlank(message = "EMAIL_NOT_BLANK")
    private String email;
    @NotBlank(message = "LOCATION_NOT_BLANK")
    private String location;
    @NotNull(message = "CAR_DATA_NOT_NULL")
    private CarDataCommand carData;
}
