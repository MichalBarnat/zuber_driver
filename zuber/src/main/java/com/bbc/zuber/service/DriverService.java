package com.bbc.zuber.service;

import com.bbc.zuber.exception.DriverNotFoundException;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.commands.UpdateDriverCommand;
import com.bbc.zuber.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;

    public Driver findById(Long id) {
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
//                    driverToEdit.setCar(command.getCar());
//                    driverToEdit.setStatus(command.getStatus());
//                    driverToEdit.setActiveLicense(command.isActiveLicense());
//                    driverToEdit.setSex(command.getSex());
//                    driverToEdit.setBalance(command.getBalance());
//                    driverToEdit.setRating(command.getRating());
//                    driverToEdit.setEmail(command.getEmail());
//                    return driverToEdit;
//                })
//                .orElseThrow(() -> new DriverNotFoundException(String.format("Driver with id: %s not found!", id)));
//    }

//    @Transactional
//    public Driver editPartially(long id, UpdateDriverCommand command) {
//        return driverRepository.findById(id)
//                .map(driverForEdit -> {
//                    Optional.ofNullable(command.getName()).ifPresent(driverForEdit::setName);
//                    Optional.ofNullable(command.getSurname()).ifPresent(driverForEdit::setSurname);
//                    Optional.ofNullable(command.getDob()).ifPresent(driverForEdit::setDob);
//                    Optional.ofNullable(command.getCar()).ifPresent(driverForEdit::setCar);
//                    Optional.ofNullable(command.getStatus()).ifPresent(driverForEdit::setStatus);
//                    Optional.ofNullable(command.isActiveLicense()).ifPresent(driverForEdit::setActiveLicense);
//                    Optional.ofNullable(command.getSex()).ifPresent(driverForEdit::setSex);
//                    Optional.ofNullable(command.getBalance()).ifPresent(driverForEdit::setBalance);
//                    Optional.ofNullable(command.getRating()).ifPresent(driverForEdit::setRating);
//                    Optional.ofNullable(command.getEmail()).ifPresent(driverForEdit::setEmail);
//
//                    return driverRepository.save(driverForEdit);
//                })
//                .orElseThrow(() -> new DriverNotFoundException(String.format("Driver with id: %s not found!", id)));
//    }
//
////    public List<Driver> findDriversWithRateGreaterThan (int rate){
////        return driverRepository.findByRateGraterThan(rate);
////    }

//    public void withdraw(Long id, BigDecimal amount) {
//        Driver account = findById(id);
//        if (account.getBalance().compareTo(amount) < 0) {
//            throw new AccoundOperationException("Unable to withdraw!");
//        }
//        BigDecimal newBalance = account.getBalance().subtract(amount);
//        account.setBalance(newBalance);
//        save(account);
//    }



//    @Transactional
//    public void setStatus(long id, Status  status){
//        Driver driver = driverRepository.findById(id)
//                .orElseThrow(DriverNotFoundException::new);
//        driver.setStatus(status);
//    }

    // TODO szukanie dostepnych driverow dla danego przejazdu

}
