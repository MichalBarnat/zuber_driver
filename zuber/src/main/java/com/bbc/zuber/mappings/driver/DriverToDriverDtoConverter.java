package com.bbc.zuber.mappings.driver;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.dto.DriverDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverToDriverDtoConverter implements Converter<Driver, DriverDto> {

    @Override
    public DriverDto convert(MappingContext<Driver, DriverDto> mappingContext) {
        Driver driver = mappingContext.getSource();

        Long carID = null;
        UUID carUUID = null;
        if(driver.getCar() != null) {
            carID = driver.getCar().getId();
            carUUID = driver.getCar().getUuid();
        }

        return DriverDto.builder()
                .id(driver.getId())
                .uuid(UUID.randomUUID())
                .name(driver.getName())
                .sex(driver.getSex())
                .location(driver.getLocation())
                .carID(carID)
                .carUUID(carUUID)
                .build();
    }
}
