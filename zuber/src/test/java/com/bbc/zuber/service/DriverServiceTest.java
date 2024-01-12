package com.bbc.zuber.service;

import com.bbc.zuber.exception.DriverNotFoundException;
import com.bbc.zuber.kafka.KafkaProducerService;
import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.UpdateDriverPartiallyCommand;
import com.bbc.zuber.repository.DriverRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.bbc.zuber.model.driver.enums.Sex.MALE;
import static com.bbc.zuber.model.driver.enums.StatusDriver.AVAILABLE;
import static com.bbc.zuber.model.driver.enums.StatusDriver.UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DriverServiceTest {

    private Driver driver;
    @Mock
    private DriverRepository driverRepository;
    @Mock
    private KafkaProducerService producerService;
    @InjectMocks
    private DriverService driverService;

    @BeforeEach
    void setUp() {
        openMocks(this);

        driver = new Driver(1L, UUID.randomUUID(), "Test",
                "Test", LocalDate.of(2000, 1, 1),
                AVAILABLE, MALE, "test.t@example.com", "Test", new Car(), false);
    }

    @Test
    void shouldGetDriver() {
        //Given
        long driverId = 1L;

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.of(driver));

        //When
        Driver result = driverService.getDriver(driverId);

        //Then
        verify(driverRepository, times(1)).findById(driverId);

        assertEquals(driver, result);
    }

    @Test
    void shouldSaveDriver() {
        //Given
        Driver driverToSave = new Driver();
        Car car = new Car();

        driverToSave.setCar(car);
        car.setDriver(driverToSave);

        when(driverRepository.save(any(Driver.class)))
                .thenReturn(driverToSave);

        //When
        Driver result = driverService.save(driverToSave, car);

        //Then
        verify(driverRepository, times(1)).save(driverToSave);
        verify(producerService, times(1)).sendDriverRegistration(driverToSave);

        assertEquals(driverToSave, result);
    }

    @Test
    void shouldFindAllDrivers() {
        //Given
        Pageable pageable = Pageable.ofSize(5).withPage(0);

        Driver driver2 = new Driver();
        List<Driver> expectedDrivers = new ArrayList<>();
        expectedDrivers.add(driver);
        expectedDrivers.add(driver2);

        when(driverRepository.findAllWithCar(pageable))
                .thenReturn(new PageImpl<>(expectedDrivers));

        //When
        Page<Driver> result = driverService.findAll(pageable);

        //Then
        verify(driverRepository, times(1)).findAllWithCar(pageable);

        assertEquals(expectedDrivers.size(), result.getContent().size());
    }

    @Test
    void shouldFindAllDeletedDrivers() {
        //Given
        Pageable pageable = Pageable.ofSize(5).withPage(0);

        Driver driver2 = new Driver();
        driver.setIsDeleted(true);
        driver2.setIsDeleted(true);

        List<Driver> deletedDrivers = new ArrayList<>();
        deletedDrivers.add(driver);
        deletedDrivers.add(driver2);

        when(driverRepository.findAllDeleted(pageable))
                .thenReturn(new PageImpl<>(deletedDrivers));

        //When
        Page<Driver> result = driverService.findAllDeleted(pageable);

        //Then
        verify(driverRepository, times(1)).findAllDeleted(pageable);
        assertEquals(driver, result.getContent().get(0));
        assertEquals(driver2, result.getContent().get(1));
    }

    @Test
    void shouldDeleteByDriverId() {
        //Given
        long driverId = 1L;

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.of(driver))
                .thenThrow(new DriverNotFoundException(driverId));

        //When
        driverService.deleteById(driverId);

        //Then
        verify(driverRepository, times(1)).findById(driverId);
        verify(driverRepository, times(1)).delete(driver);

        assertThrows(DriverNotFoundException.class, () -> driverService.deleteById(driverId));
    }

    @Test
    void shouldEditPartiallyDriver() {
        //Given
        long driverId = 1L;

        UpdateDriverPartiallyCommand command = UpdateDriverPartiallyCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .build();

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.of(driver));

        //When
        Driver result = driverService.editPartially(driverId, command);

        //Then
        verify(driverRepository, times(1)).findById(driverId);
        verify(producerService, times(1)).sendDriverEdited(result);

        assertEquals(command.getName(), result.getName());
        assertEquals(command.getSurname(), result.getSurname());
        assertEquals(command.getDob(), result.getDob());
        assertEquals(command.getStatusDriver(), result.getStatusDriver());
        assertEquals(command.getSex(), result.getSex());
        assertEquals(command.getEmail(), result.getEmail());
        assertEquals(command.getLocation(), result.getLocation());
    }

    @Test
    void shouldReturnTrueWhenDriverExistsByEmail() {
        //Given
        String existingEmail = "test.t@example.com";

        when(driverRepository.existsByEmail(existingEmail))
                .thenReturn(true);

        //When
        boolean result = driverService.existsByEmail(existingEmail);

        //Then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenDriverDoesNotExistsByEmail() {
        //Given
        String nonExistingEmail = "test.test@example.com";

        when(driverRepository.existsByEmail(nonExistingEmail))
                .thenReturn(false);

        //When
        boolean result = driverService.existsByEmail(nonExistingEmail);

        //Then
        assertFalse(result);
    }

    @Test
    void shouldSetStatusForDriver() {
        //Given
        long driverId = 1L;

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.of(driver));

        //When
        driverService.setStatus(driverId, UNAVAILABLE);

        //Then
        verify(driverRepository, times(1)).findById(driverId);
        assertEquals(UNAVAILABLE, driver.getStatusDriver());
    }

    @Test
    void shouldThrowDriverNotFoundExceptionWhenDriverIdDoesNotExistsForGetDriverMethod() {
        //Given
        long driverId = 1L;

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.empty());

        //When
        DriverNotFoundException exception = assertThrows(
                DriverNotFoundException.class,
                () -> driverService.getDriver(driverId)
        );

        //Then
        verify(driverRepository, times(1)).findById(driverId);

        assertEquals("Driver with id: 1 not found!", exception.getMessage());
    }

    @Test
    void shouldThrowDriverNotFoundExceptionWhenDriverIdDoesNotExistsForDeleteByIdMethod() {
        //Given
        long driverId = 1L;

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.empty());

        //When
        DriverNotFoundException exception = assertThrows(
                DriverNotFoundException.class,
                () -> driverService.deleteById(driverId)
        );

        //Then
        verify(driverRepository, times(1)).findById(driverId);
        verify(driverRepository, never()).delete(driver);

        assertEquals("Driver with id: 1 not found!", exception.getMessage());
    }

    @Test
    void shouldThrowDriverNotFoundExceptionWhenDriverIdDoesNotExistsForEditPartiallyMethod() {
        //Given
        long driverId = 1L;

        UpdateDriverPartiallyCommand command = UpdateDriverPartiallyCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .build();

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.empty());

        //When
        DriverNotFoundException exception = assertThrows(
                DriverNotFoundException.class,
                () -> driverService.editPartially(driverId, command)
        );

        //Then
        verify(driverRepository, times(1)).findById(driverId);
        verify(producerService, never()).sendDriverEdited(any(Driver.class));

        assertEquals("Driver with id: 1 not found!", exception.getMessage());
    }

    @Test
    void shouldThrowDriverNotFoundExceptionWhenDriverIdDoesNotExistsForSetStatusMethod() {
        //Given
        long driverId = 1L;

        when(driverRepository.findById(driverId))
                .thenReturn(Optional.empty());

        //When
        DriverNotFoundException exception = assertThrows(
                DriverNotFoundException.class,
                () -> driverService.setStatus(driverId, UNAVAILABLE)
        );

        //Then
        verify(driverRepository, times(1)).findById(driverId);
        assertEquals("Driver with id: 1 not found!", exception.getMessage());
    }
}