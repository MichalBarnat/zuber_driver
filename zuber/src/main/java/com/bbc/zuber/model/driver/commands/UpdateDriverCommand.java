package com.bbc.zuber.model.driver.commands;

import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateDriverCommand {
    private String name;
    private String surname;
    private String dob;
    private StatusDriver statusDriver;
    private Sex sex;
    private String email;
}
