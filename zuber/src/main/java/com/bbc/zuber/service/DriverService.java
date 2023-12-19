package com.bbc.zuber.service;

import com.bbc.zuber.exception.DriverNotFoundException;
import com.bbc.zuber.kafka.KafkaProducerService;
import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.UpdateDriverPartiallyCommand;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import com.bbc.zuber.model.driver.response.DriverResponse;
import com.bbc.zuber.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final KafkaProducerService producerService;

    @Transactional(readOnly = true)
    public Driver getDriver(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));
    }

    @Transactional
    public Driver save(Driver driver, Car car) {
        car.setDriver(driver);
        driver.setCar(car);
        Driver savedDriver = driverRepository.save(driver);
        producerService.sendDriverRegistration(savedDriver);
        return savedDriver;
    }

    @Transactional(readOnly = true)
    public Page<Driver> findAll(Pageable pageable) {
        return driverRepository.findAllWithCar(pageable);
    }

    @Transactional
    public DriverResponse deleteById(Long id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));

        driverRepository.delete(driver);

        return DriverResponse.builder()
                .message("Deleted successfully.")
                .build();
    }

    @Transactional
    public Driver editPartially(long id, UpdateDriverPartiallyCommand command) {
        Driver editedDriver = driverRepository.findById(id)
                .map(driverToEdit -> {
                    Optional.ofNullable(command.getName()).ifPresent(driverToEdit::setName);
                    Optional.ofNullable(command.getSurname()).ifPresent(driverToEdit::setSurname);
                    Optional.ofNullable(command.getDob()).ifPresent(driverToEdit::setDob);
                    Optional.ofNullable(command.getStatusDriver()).ifPresent(driverToEdit::setStatusDriver);
                    Optional.ofNullable(command.getSex()).ifPresent(driverToEdit::setSex);
                    Optional.ofNullable(command.getEmail()).ifPresent(driverToEdit::setEmail);
                    Optional.ofNullable(command.getLocation()).ifPresent(driverToEdit::setLocation);
                    return driverToEdit;
                })
                .orElseThrow(() -> new DriverNotFoundException(id));

        producerService.sendDriverEdited(editedDriver);
        return editedDriver;
    }

    public boolean existsByEmail(String email) {
        return driverRepository.existsByEmail(email);
    }

//    public void withdraw(Long id, BigDecimal amount) {
//        Driver account = findById(id);
//        if (account.getBalance().compareTo(amount) < 0) {
//            throw new AccoundOperationException("Unable to withdraw!");
//        }
//        BigDecimal newBalance = account.getBalance().subtract(amount);
//        account.setBalance(newBalance);
//        processAndSave(account);
//    }

    @Transactional
    public void setStatus(long id, StatusDriver statusDriver) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new DriverNotFoundException(id));

        driver.setStatusDriver(statusDriver);
    }

}
