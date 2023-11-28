package com.bbc.zuber.mapping;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.CreateDriverCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateDriverCommandToDriverConverter implements Converter<CreateDriverCommand, Driver> {

    @Override
    public Driver convert(MappingContext<CreateDriverCommand, Driver> mappingContext) {
        CreateDriverCommand command = mappingContext.getSource();

        return Driver.builder()
                .uuid(UUID.randomUUID())
                .name(command.getName())
                .surname(command.getSurname())
                .dob(command.getDob())
                .statusDriver(command.getStatusDriver())
                .sex(command.getSex())
                .email(command.getEmail())
                .build();
    }
}
