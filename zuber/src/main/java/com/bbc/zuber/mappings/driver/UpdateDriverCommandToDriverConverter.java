package com.bbc.zuber.mappings.driver;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.UpdateDriverPartiallyCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class UpdateDriverCommandToDriverConverter implements Converter<UpdateDriverPartiallyCommand, Driver> {

    @Override
    public Driver convert(MappingContext<UpdateDriverPartiallyCommand, Driver> mappingContext) {
        UpdateDriverPartiallyCommand command = mappingContext.getSource();

        return Driver.builder()
                .name(command.getName())
                .surname(command.getSurname())
                .dob(command.getDob())
                .statusDriver(command.getStatusDriver())
                .sex(command.getSex())
                .email(command.getEmail())
                .location(command.getLocation())
                .build();
    }
}
