package com.bbc.zuber.mappings.driver;

import com.bbc.zuber.model.car.command.CarDataCommand;
import com.bbc.zuber.model.car.enums.TypeOfCar;
import com.bbc.zuber.model.car.enums.TypeOfEngine;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.CreateDriverCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDate;

import static com.bbc.zuber.model.car.enums.TypeOfCar.STANDARD;
import static com.bbc.zuber.model.car.enums.TypeOfEngine.PETROL;
import static com.bbc.zuber.model.driver.enums.Sex.MALE;
import static com.bbc.zuber.model.driver.enums.StatusDriver.AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateDriverCommandToDriverConverterTest {

    @Mock
    private MappingContext<CreateDriverCommand, Driver> mappingContext;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldConvertCreateDriverCommandToDriver() {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("Test")
                .model("Test")
                .productionYear(2020)
                .engine(PETROL)
                .type(STANDARD)
                .size(5)
                .plateNum("Test")
                .build();

        CreateDriverCommand command = CreateDriverCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .carData(carCommand)
                .build();

        CreateDriverCommandToDriverConverter converter = new CreateDriverCommandToDriverConverter();

        when(mappingContext.getSource())
                .thenReturn(command);

        //When
        Driver result = converter.convert(mappingContext);

        //Then
        verify(mappingContext, times(1)).getSource();

        assertEquals(command.getName(), result.getName());
        assertEquals(command.getSurname(), result.getSurname());
        assertEquals(command.getDob(), result.getDob());
        assertEquals(command.getStatusDriver(), result.getStatusDriver());
        assertEquals(command.getSex(), result.getSex());
        assertEquals(command.getEmail(), result.getEmail());

        assertNotNull(result.getCar());
    }
}