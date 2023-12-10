package com.bbc.zuber.mappings.car;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.car.command.CarDataCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CarDataCommandToCarConverter implements Converter<CarDataCommand, Car> {
    @Override
    public Car convert(MappingContext<CarDataCommand, Car> mappingContext) {
        CarDataCommand command = mappingContext.getSource();

        return Car.builder()
                .uuid(UUID.randomUUID())
                .brand(command.getBrand())
                .model(command.getModel())
                .productionYear(command.getProductionYear())
                .engine(command.getEngine())
                .type(command.getType())
                .size(command.getSize())
                .plateNum(command.getPlateNum())
                .build();
    }
}
