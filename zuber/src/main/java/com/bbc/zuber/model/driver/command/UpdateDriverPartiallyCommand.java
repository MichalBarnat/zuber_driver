package com.bbc.zuber.model.driver.command;

import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import com.bbc.zuber.validations.email.UniqueEmail;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.STRING;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateDriverPartiallyCommand {

    private String name;
    private String surname;
    private LocalDate dob;
    @Enumerated(STRING)
    private StatusDriver statusDriver;
    @Enumerated(STRING)
    private Sex sex;
    @UniqueEmail(message = "GIVEN_EMAIL_EXISTS")
    @Email(message = "INCORRECT_EMAIL_FORMAT")
    private String email;
    private String location;
//    private CarDataCommand carData;
}
