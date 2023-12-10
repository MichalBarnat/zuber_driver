package com.bbc.zuber.mappings.car;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.car.dto.CarDto;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class CarToCarDtoConverter implements Converter<Car, CarDto> {
    @Override
    public CarDto convert(MappingContext<Car, CarDto> mappingContext) {
        Car car = mappingContext.getSource();

        return CarDto.builder()
                .id(car.getId())
                .uuid(car.getUuid())
                .brand(car.getBrand())
                .model(car.getModel())
                .productionYear(car.getProductionYear())
                .type(car.getType())
                .size(car.getSize())
                .plateNum(car.getPlateNum())
                .build();
    }
}
