package com.bbc.zuber.controller;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.CreateDriverCommand;
import com.bbc.zuber.model.driver.command.UpdateDriverPartiallyCommand;
import com.bbc.zuber.model.driver.dto.DriverDto;
import com.bbc.zuber.model.driver.response.DriverResponse;
import com.bbc.zuber.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Slf4j
public class DriverController {

    private final DriverService driverService;
    private final ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriver(@PathVariable Long id) {
        DriverDto dto = modelMapper.map(driverService.getDriver(id), DriverDto.class);
        return new ResponseEntity<>(dto, OK);
    }

    @GetMapping
    public ResponseEntity<Page<DriverDto>> findAll(@PageableDefault Pageable pageable) {
        Page<DriverDto> dtos = driverService.findAll(pageable)
                .map(driver -> modelMapper.map(driver, DriverDto.class));
        return new ResponseEntity<>(dtos, OK);
    }

    @GetMapping("/deleted")
    public ResponseEntity<Page<DriverDto>> findAllDeleted(@PageableDefault Pageable pageable) {
        Page<DriverDto> dtos = driverService.findAllDeleted(pageable)
                .map(driver -> modelMapper.map(driver, DriverDto.class));
        return new ResponseEntity<>(dtos, OK);
    }

    @PostMapping
    public ResponseEntity<DriverDto> save(@RequestBody @Valid CreateDriverCommand command) {
        Driver driverToSave = modelMapper.map(command, Driver.class);
        Car car = modelMapper.map(command.getCarData(), Car.class);
        DriverDto dto = modelMapper.map(driverService.save(driverToSave, car), DriverDto.class);
        return new ResponseEntity<>(dto, CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverResponse> delete(@PathVariable Long id) {
        DriverResponse response = driverService.deleteById(id);
        return new ResponseEntity<>(response, NO_CONTENT);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DriverDto> edit(@PathVariable Long id, @RequestBody @Valid UpdateDriverPartiallyCommand command) {
        Driver editedDriver = driverService.editPartially(id, command);
        DriverDto dto = modelMapper.map(editedDriver, DriverDto.class);
        return new ResponseEntity<>(dto, OK);
    }
}
