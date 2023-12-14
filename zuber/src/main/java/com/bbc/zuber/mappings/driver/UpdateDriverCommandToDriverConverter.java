package com.bbc.zuber.mappings.driver;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.UpdateDriverCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UpdateDriverCommandToDriverConverter implements Converter<UpdateDriverCommand, Driver> {

    @Override
    public Driver convert(MappingContext<UpdateDriverCommand, Driver> mappingContext) {
        UpdateDriverCommand command = mappingContext.getSource();

        return Driver.builder()
                .name(command.getName())
                .surname(command.getSurname())
                .dob(LocalDate.parse(command.getDob()))
                .statusDriver(command.getStatusDriver())
                .sex(command.getSex())
                .email(command.getEmail())
                .location(command.getLocation())
                .build();
    }
}
