package com.bbc.zuber.mappings.driver;

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
        Driver driver = mappingContext.getSource();

        return DriverDto.builder()
                .id(driver.getId())
                .uuid(UUID.randomUUID())
                .name(driver.getName())
                .sex(driver.getSex())
                .location(driver.getLocation())
                .car(driver.getCar())
                .build();
    }
}
