package com.bbc.zuber.mappings.driver;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.dto.DriverDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDate;
import java.util.UUID;

import static com.bbc.zuber.model.driver.enums.Sex.MALE;
import static com.bbc.zuber.model.driver.enums.StatusDriver.AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DriverToDriverDtoConverterTest {

    @Mock
    private MappingContext<Driver, DriverDto> mappingContext;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldConvertDriverToDriverDto() {
        //Given
        Driver driver = Driver.builder()
                .id(1L)
                .uuid(UUID.randomUUID())
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .car(new Car())
                .build();

        DriverToDriverDtoConverter converter = new DriverToDriverDtoConverter();

        when(mappingContext.getSource())
                .thenReturn(driver);

        //When
        DriverDto result = converter.convert(mappingContext);

        //Then
        verify(mappingContext, times(1)).getSource();

        assertEquals(driver.getName(), result.getName());
        assertEquals(driver.getSex(), result.getSex());
        assertEquals(driver.getLocation(), result.getLocation());
    }
}