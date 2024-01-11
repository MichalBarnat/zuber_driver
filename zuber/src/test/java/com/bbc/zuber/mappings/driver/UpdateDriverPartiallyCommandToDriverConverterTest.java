package com.bbc.zuber.mappings.driver;

import com.bbc.zuber.model.driver.Driver;
import com.bbc.zuber.model.driver.command.UpdateDriverPartiallyCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDate;

import static com.bbc.zuber.model.driver.enums.Sex.MALE;
import static com.bbc.zuber.model.driver.enums.StatusDriver.AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UpdateDriverPartiallyCommandToDriverConverterTest {

    @Mock
    private MappingContext<UpdateDriverPartiallyCommand, Driver> mappingContext;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void shouldConvertUpdateDriverPartiallyCommandToDriver() {
        //Given
        UpdateDriverPartiallyCommand command = UpdateDriverPartiallyCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .build();

        UpdateDriverPartiallyCommandToDriverConverter converter = new UpdateDriverPartiallyCommandToDriverConverter();

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
        assertEquals(command.getLocation(), result.getLocation());
    }
}