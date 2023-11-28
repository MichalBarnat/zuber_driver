package com.bbc.zuber.service;

import com.bbc.zuber.exception.DriverNotFoundException;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.UpdateDriverCommand;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import com.bbc.zuber.repository.DriverRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver getDriver(Long id) {
        return driverRepository.findById(id).orElseThrow(() -> new DriverNotFoundException(
                String.format("Driver with id %d not found!", id)));
    }

    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    public Page<Driver> findAll(Pageable pageable) {
        return driverRepository.findAll(pageable);
    }

    public void deleteById(long id) {
        if (driverRepository.existsById(id)) {
            driverRepository.deleteById(id);
        } else {
            throw new DriverNotFoundException();
        }
    }

    public void deleteAll() {
        driverRepository.deleteAll();
    }

//    @Transactional
//    public Driver editDriver(long id, UpdateDriverCommand command) {
//        return driverRepository.findById(id)
//                .map(driverToEdit -> {
//                    driverToEdit.setName(command.getName());
//                    driverToEdit.setSurname(command.getSurname());
//                    driverToEdit.setDob(command.getDob());
//                    driverToEdit.setStatusDriver(command.getStatusDriver());
//                    driverToEdit.setSex(command.getSex());
//                    driverToEdit.setEmail(command.getEmail());
//                    return driverToEdit;
//                })
//                .orElseThrow(() -> new DriverNotFoundException(String.format("Driver with id: %s not found!", id)));
//    }

    @Transactional
    public Driver edit(Driver driver) {
        return driverRepository.findById(driver.getId())
                .map(userToEdit -> {
                    Optional.ofNullable(driver.getName()).ifPresent(userToEdit::setName);
                    Optional.ofNullable(driver.getSurname()).ifPresent(userToEdit::setSurname);
                    Optional.ofNullable(driver.getDob()).ifPresent(userToEdit::setDob);
                    Optional.ofNullable(driver.getStatusDriver());
                    Optional.ofNullable(driver.getSex()).ifPresent(userToEdit::setSex);
                    Optional.ofNullable(driver.getEmail()).ifPresent(userToEdit::setEmail);
                    return userToEdit;
                }).orElseThrow(() -> new DriverNotFoundException());
    }


//    public void withdraw(Long id, BigDecimal amount) {
//        Driver account = findById(id);
//        if (account.getBalance().compareTo(amount) < 0) {
//            throw new AccoundOperationException("Unable to withdraw!");
//        }
//        BigDecimal newBalance = account.getBalance().subtract(amount);
//        account.setBalance(newBalance);
//        save(account);
//    }

    @Transactional
    public void setStatus(long id, StatusDriver statusDriver){
        Driver driver = driverRepository.findById(id)
                .orElseThrow(DriverNotFoundException::new);
        driver.setStatusDriver(statusDriver);
    }

    // TODO szukanie dostepnych driverow dla danego przejazdu

}
