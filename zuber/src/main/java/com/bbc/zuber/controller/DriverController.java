package com.bbc.zuber.controller;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.CreateDriverCommand;
import com.bbc.zuber.model.driver.command.UpdateDriverCommand;
import com.bbc.zuber.model.driver.dto.DriverDto;
import com.bbc.zuber.service.DriverService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Slf4j
public class DriverController {

    private final DriverService driverService;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/{id}")
    public Driver getDriver(@PathVariable Long id) {
        return driverService.getDriver(id);
    }

    @PostMapping
    public ResponseEntity<DriverDto> save(@RequestBody CreateDriverCommand command) throws JsonProcessingException {
        Driver driverToSave = modelMapper.map(command, Driver.class);
        Car car = modelMapper.map(command.getCarData(), Car.class);
        driverToSave.setCar(car);
        car.setDriver(driverToSave);


        Driver savedDriver = driverService.save(driverToSave);
        String savedDriverJson = objectMapper.writeValueAsString(savedDriver);
        kafkaTemplate.send("driver-registration", savedDriverJson);
        return ResponseEntity.ok(modelMapper.map(savedDriver, DriverDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        driverService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> edit(@PathVariable Long id, @RequestBody UpdateDriverCommand command) throws JsonProcessingException {
        Driver driverToEdit = modelMapper.map(command, Driver.class);
        driverToEdit.setId(id);
        Driver editedDriver = driverService.edit(driverToEdit);
        String editedDriverJson = objectMapper.writeValueAsString(editedDriver);
        kafkaTemplate.send("driver-edited", editedDriver);
        return ResponseEntity.ok(modelMapper.map(editedDriver, DriverDto.class));
    }

}
