package com.bbc.zuber.mappings.car;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.car.command.CarDataCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.spi.MappingContext;

import static com.bbc.zuber.model.car.enums.TypeOfCar.STANDARD;
import static com.bbc.zuber.model.car.enums.TypeOfEngine.PETROL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CarDataCommandToCarConverterTest {

    @Mock
    private MappingContext<CarDataCommand, Car> mappingContext;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldConvertCarDataCommandToCar() {
        //Given
        CarDataCommand command = CarDataCommand.builder()
                .brand("Test")
                .model("Test")
                .productionYear(2020)
                .engine(PETROL)
                .type(STANDARD)
                .size(1)
                .plateNum("Test")
                .build();

        CarDataCommandToCarConverter converter = new CarDataCommandToCarConverter();

        when(mappingContext.getSource())
                .thenReturn(command);

        //When
        Car result = converter.convert(mappingContext);

        //Then
        verify(mappingContext, times(1)).getSource();

        assertEquals(command.getBrand(), result.getBrand());
        assertEquals(command.getModel(), result.getModel());
        assertEquals(command.getProductionYear(), result.getProductionYear());
        assertEquals(command.getEngine(), result.getEngine());
        assertEquals(command.getType(), result.getType());
        assertEquals(command.getSize(), result.getSize());
        assertEquals(command.getPlateNum(), result.getPlateNum());
    }
}