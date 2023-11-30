package com.bbc.zuber.controller;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.CreateDriverCommand;
import com.bbc.zuber.model.driver.command.UpdateDriverCommand;
import com.bbc.zuber.model.driver.dto.DriverDto;
import com.bbc.zuber.service.DriverService;
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
    private final KafkaTemplate<String, Driver> kafkaTemplate;

    @GetMapping("/{id}")
    public Driver getDriver(@PathVariable Long id) {
        return driverService.getDriver(id);
    }

    @PostMapping
    public ResponseEntity<DriverDto> save(@RequestBody CreateDriverCommand command) {
        Driver driverToSave = modelMapper.map(command, Driver.class);
        Driver savedDriver = driverService.save(driverToSave);
        kafkaTemplate.send("driver-registration", savedDriver);
        return ResponseEntity.ok(modelMapper.map(savedDriver, DriverDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        driverService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverDto> edit(@PathVariable Long id, @RequestBody UpdateDriverCommand command) {
        Driver driverToEdit = modelMapper.map(command, Driver.class);
        driverToEdit.setId(id);
        Driver editedDriver = driverService.edit(driverToEdit);
        kafkaTemplate.send("driver-edited", editedDriver);
        return ResponseEntity.ok(modelMapper.map(editedDriver, DriverDto.class));
    }

}
