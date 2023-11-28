package com.bbc.zuber.mapping;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.dto.DriverDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DriverToDriverDtoConverter implements Converter<Driver, DriverDto> {

    @Override
    public DriverDto convert(MappingContext<Driver, DriverDto> mappingContext) {
        Driver command = mappingContext.getSource();

        return DriverDto.builder()
                .id(command.getId())
                .uuid(UUID.randomUUID())
                .name(command.getName())
                .sex(command.getSex())
                .build();
    }
}
