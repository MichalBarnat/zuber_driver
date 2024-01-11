package com.bbc.zuber.mappings.car;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.car.dto.CarDto;
import com.bbc.zuber.model.driver.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.spi.MappingContext;

import java.util.UUID;

import static com.bbc.zuber.model.car.enums.TypeOfCar.STANDARD;
import static com.bbc.zuber.model.car.enums.TypeOfEngine.PETROL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CarToCarDtoConverterTest {

    @Mock
    private MappingContext<Car, CarDto> mappingContext;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldConvertCarToCarDto() {
        //Given
        Car car = Car.builder()
                .id(1L)
                .uuid(UUID.randomUUID())
                .brand("Test")
                .model("Test")
                .productionYear(1)
                .engine(PETROL)
                .type(STANDARD)
                .size(5)
                .plateNum("TEst")
                .driver(new Driver())
                .build();

        CarToCarDtoConverter converter = new CarToCarDtoConverter();

        when(mappingContext.getSource())
                .thenReturn(car);

        //When
        CarDto result = converter.convert(mappingContext);

        //Then
        verify(mappingContext, times(1)).getSource();

        assertEquals(car.getBrand(), result.getBrand());
        assertEquals(car.getModel(), result.getModel());
        assertEquals(car.getProductionYear(), result.getProductionYear());
        assertEquals(car.getType(), result.getType());
        assertEquals(car.getSize(), result.getSize());
        assertEquals(car.getPlateNum(), result.getPlateNum());
    }
}