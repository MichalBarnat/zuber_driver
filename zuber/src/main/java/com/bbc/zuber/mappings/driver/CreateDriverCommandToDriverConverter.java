package com.bbc.zuber.mappings.driver;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.CreateDriverCommand;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CreateDriverCommandToDriverConverter implements Converter<CreateDriverCommand, Driver> {

    @Override
    public Driver convert(MappingContext<CreateDriverCommand, Driver> mappingContext) {
        CreateDriverCommand command = mappingContext.getSource();

        Car car = null;
        if(command.getCarData() != null) {
            car = new Car();
            car.setUuid(UUID.randomUUID());
            car.setBrand(command.getCarData().getBrand());
            car.setModel(command.getCarData().getModel());
            car.setProductionYear(command.getCarData().getProductionYear());
            car.setEngine(command.getCarData().getEngine());
            car.setType(command.getCarData().getType());
            car.setSize(command.getCarData().getSize());
            car.setPlateNum(command.getCarData().getPlateNum());
        }

        return Driver.builder()
                .uuid(UUID.randomUUID())
                .name(command.getName())
                .surname(command.getSurname())
                .dob(command.getDob())
                .statusDriver(command.getStatusDriver())
                .sex(command.getSex())
                .email(command.getEmail())
                .location(command.getLocation())
                .car(car)
                .build();
    }
}
