package com.bbc.zuber.mappings.driver;

import com.bbc.zuber.model.car.dto.CarDto;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.dto.DriverDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverToDriverDtoConverter implements Converter<Driver, DriverDto> {

    @Override
    public DriverDto convert(MappingContext<Driver, DriverDto> mappingContext) {
        Driver driver = mappingContext.getSource();
        CarDto carDto = null;
        if(driver.getCar() != null) {
            carDto = new CarDto(driver.getCar().getId(),
                    driver.getCar().getUuid(),
                    driver.getCar().getBrand(),
                    driver.getCar().getModel(),
                    driver.getCar().getProductionYear(),
                    driver.getCar().getType(),
                    driver.getCar().getSize(),
                    driver.getCar().getPlateNum());
        }

        return DriverDto.builder()
                .id(driver.getId())
                .uuid(UUID.randomUUID())
                .name(driver.getName())
                .sex(driver.getSex())
                .location(driver.getLocation())
                .carDto(carDto)
                .build();
    }
}
