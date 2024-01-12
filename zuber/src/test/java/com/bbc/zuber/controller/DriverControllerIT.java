package com.bbc.zuber.controller;

import com.bbc.zuber.DatabaseCleaner;
import com.bbc.zuber.DriverAppApplication;
import com.bbc.zuber.exception.dto.ValidationErrorDto;
import com.bbc.zuber.model.car.command.CarDataCommand;
import com.bbc.zuber.model.driver.command.CreateDriverCommand;
import com.bbc.zuber.model.driver.command.UpdateDriverPartiallyCommand;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static com.bbc.zuber.model.car.enums.TypeOfCar.STANDARD;
import static com.bbc.zuber.model.car.enums.TypeOfEngine.PETROL;
import static com.bbc.zuber.model.driver.enums.Sex.MALE;
import static com.bbc.zuber.model.driver.enums.StatusDriver.AVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = DriverAppApplication.class)
@AutoConfigureMockMvc
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {
                "listeners=PLAINTEXT://localhost:9092",
                "port=9092"
        },
        controlledShutdown = true,
        brokerPropertiesLocation = "classpath:embedded-kafka-broker-test.yml"
)
@DirtiesContext
@ActiveProfiles("test")
class DriverControllerIT {

    private final MockMvc postman;
    private final ObjectMapper objectMapper;
    private final DatabaseCleaner databaseCleaner;

    @Autowired
    public DriverControllerIT(MockMvc postman, ObjectMapper objectMapper, DatabaseCleaner databaseCleaner) {
        this.postman = postman;
        this.objectMapper = objectMapper;
        this.databaseCleaner = databaseCleaner;
    }

    @AfterEach
    void tearDown() throws LiquibaseException {
        databaseCleaner.cleanUp();
    }

    @Test
    void shouldGetDriver() throws Exception {
        //Given
        //When
        //Then
        postman.perform(get("/api/drivers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.uuid").value("ce7a9a6e-b4fd-4a1d-8e79-ea5a3e2d6d95"))
                .andExpect(jsonPath("$.name").value("Kazek"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andExpect(jsonPath("$.location").value("Tczewska 22, 83-032 Pszczółki"));
    }

    @Test
    void shouldFindAllDrivers() throws Exception {
        //Given
        //When
        //Then
        postman.perform(get("/api/drivers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
//                .andExpect(jsonPath("$.content[0].uuid").value("ce7a9a6e-b4fd-4a1d-8e79-ea5a3e2d6d95"))
                .andExpect(jsonPath("$.content[0].name").value("Kazek"))
                .andExpect(jsonPath("$.content[0].sex").value("MALE"))
                .andExpect(jsonPath("$.content[0].location").value("Tczewska 22, 83-032 Pszczółki"))
                .andExpect(jsonPath("$.content[1].id").value(2))
//                .andExpect(jsonPath("$.content[1].uuid").value("a5c8d2f9-2f4a-4b5d-9b5c-1af0e4f7a24e"))
                .andExpect(jsonPath("$.content[1].name").value("Ania"))
                .andExpect(jsonPath("$.content[1].sex").value("FEMALE"))
                .andExpect(jsonPath("$.content[1].location").value("Bolesława Chrobrego 6, 73-108 Kobylanka"))
                .andExpect(jsonPath("$.content[2].id").value(3))
//                .andExpect(jsonPath("$.content[2].uuid").value("7e5a77fd-8f73-4d29-8c8c-1f96e0b4e3d7"))
                .andExpect(jsonPath("$.content[2].name").value("Robert"))
                .andExpect(jsonPath("$.content[2].sex").value("MALE"))
                .andExpect(jsonPath("$.content[2].location").value("Rybaki 1, 18-400 Łomża"));
    }

    @Test
    void shouldFindAllDeletedDrivers() throws Exception {
        //Given
        postman.perform(get("/api/drivers/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(4))
//                .andExpect(jsonPath("$.uuid").value("ce7a9a6e-b4fd-4a1d-8e79-ea5a3e2d6d95"))
                .andExpect(jsonPath("$.name").value("Sara"))
                .andExpect(jsonPath("$.sex").value("FEMALE"))
                .andExpect(jsonPath("$.location").value("Aleja Wolności 12, 62-800 Kalisz"));

        postman.perform(get("/api/drivers/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
//                .andExpect(jsonPath("$.uuid").value("a5c8d2f9-2f4a-4b5d-9b5c-1af0e4f7a24e"))
                .andExpect(jsonPath("$.name").value("Dobromir"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andExpect(jsonPath("$.location").value("Śląska 55A, 22-400 Zamość"));

        //When
        postman.perform(delete("/api/drivers/4"))
                .andDo(print())
                .andExpect(status().isNoContent());

        postman.perform(delete("/api/drivers/5"))
                .andDo(print())
                .andExpect(status().isNoContent());

        //Then
        postman.perform(get("/api/drivers/deleted")
                        .param("size", "5")
                        .param("page", "0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(4))
//                .andExpect(jsonPath("$.content[0].uuid").value("ce7a9a6e-b4fd-4a1d-8e79-ea5a3e2d6d95"))
                .andExpect(jsonPath("$.content[0].name").value("Sara"))
                .andExpect(jsonPath("$.content[0].sex").value("FEMALE"))
                .andExpect(jsonPath("$.content[0].location").value("Aleja Wolności 12, 62-800 Kalisz"))
                .andExpect(jsonPath("$.content[1].id").value(5))
//                .andExpect(jsonPath("$.content[1].uuid").value("a5c8d2f9-2f4a-4b5d-9b5c-1af0e4f7a24e"))
                .andExpect(jsonPath("$.content[1].name").value("Dobromir"))
                .andExpect(jsonPath("$.content[1].sex").value("MALE"))
                .andExpect(jsonPath("$.content[1].location").value("Śląska 55A, 22-400 Zamość"));
    }

    @Test
    void shouldSaveDriver() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
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

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.sex").value(command.getSex().toString()))
                .andExpect(jsonPath("$.location").value(command.getLocation()));

        //Then
        postman.perform(get("/api/drivers/6")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.sex").value(command.getSex().toString()))
                .andExpect(jsonPath("$.location").value(command.getLocation()));
    }

    @Test
    void shouldDeleteDriver() throws Exception {
        //Given
        //When
        postman.perform(get("/api/drivers/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.uuid").value("ce7a9a6e-b4fd-4a1d-8e79-ea5a3e2d6d95"))
                .andExpect(jsonPath("$.name").value("Kazek"))
                .andExpect(jsonPath("$.sex").value("MALE"))
                .andExpect(jsonPath("$.location").value("Tczewska 22, 83-032 Pszczółki"));

        postman.perform(delete("/api/drivers/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        //Then
        postman.perform(get("/api/drivers/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 1 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/1"))
                .andExpect(jsonPath("$.method").value("GET"));
    }

    @Test
    void shouldEditDriverPartially() throws Exception {
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

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
//                .andExpect(jsonPath("$.uuid").value("a5c8d2f9-2f4a-4b5d-9b5c-1af0e4f7a24e"))
                .andExpect(jsonPath("$.name").value("Ania"))
                .andExpect(jsonPath("$.sex").value("FEMALE"))
                .andExpect(jsonPath("$.location").value("Bolesława Chrobrego 6, 73-108 Kobylanka"));

        postman.perform(patch("/api/drivers/2")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
//                .andExpect(jsonPath("$.uuid").value("a5c8d2f9-2f4a-4b5d-9b5c-1af0e4f7a24e"))
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.sex").value(command.getSex().toString()))
                .andExpect(jsonPath("$.location").value(command.getLocation()));

        //Then
        postman.perform(get("/api/drivers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
//                .andExpect(jsonPath("$.uuid").value("a5c8d2f9-2f4a-4b5d-9b5c-1af0e4f7a24e"))
                .andExpect(jsonPath("$.name").value(command.getName()))
                .andExpect(jsonPath("$.sex").value(command.getSex().toString()))
                .andExpect(jsonPath("$.location").value(command.getLocation()));
    }

    @Test
    void shouldNotGetDriverWhenDriverIdDoesNotExists() throws Exception {
        //Given
        //When
        //Then
        postman.perform(get("/api/drivers/20"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 20 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/20"))
                .andExpect(jsonPath("$.method").value("GET"));
    }

    @Test
    void shouldNotDeleteDriverWhenDriverIdDoesNotExists() throws Exception {
        //Given
        //When
        postman.perform(get("/api/drivers/20"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 20 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/20"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        postman.perform(delete("/api/drivers/20"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 20 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/20"))
                .andExpect(jsonPath("$.method").value("DELETE"));
    }

    @Test
    void shouldNotSaveDriverWhenNameIsBlank() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
                .productionYear(2020)
                .engine(PETROL)
                .type(STANDARD)
                .size(5)
                .plateNum("Test")
                .build();

        CreateDriverCommand command = CreateDriverCommand.builder()
                .name("")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'name' && @.code == 'NAME_NOT_BLANK')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenSurnameIsBlank() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
                .productionYear(2020)
                .engine(PETROL)
                .type(STANDARD)
                .size(5)
                .plateNum("Test")
                .build();

        CreateDriverCommand command = CreateDriverCommand.builder()
                .name("Tes")
                .surname("")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'surname' && @.code == 'SURNAME_NOT_BLANK')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenDobIsFutureOrPresent() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
                .productionYear(2020)
                .engine(PETROL)
                .type(STANDARD)
                .size(5)
                .plateNum("Test")
                .build();

        CreateDriverCommand command = CreateDriverCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2030, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'dob' && @.code == 'DOB_CANNOT_BE_FUTURE_OR_PRESENT')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenDobIsNull() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
                .productionYear(2020)
                .engine(PETROL)
                .type(STANDARD)
                .size(5)
                .plateNum("Test")
                .build();

        CreateDriverCommand command = CreateDriverCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(null)
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'dob' && @.code == 'DOB_NOT_NULL')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenStatusDriverIsNull() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
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
                .statusDriver(null)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'statusDriver' && @.code == 'STATUS_DRIVER_NOT_NULL')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenSexIsNull() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
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
                .sex(null)
                .email("test.t@example.com")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'sex' && @.code == 'SEX_NOT_NULL')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenEmailExists() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
                .productionYear(2020)
                .engine(PETROL)
                .type(STANDARD)
                .size(5)
                .plateNum("Test")
                .build();

        CreateDriverCommand command = CreateDriverCommand.builder()
                .name("Tst")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("kazek.hulaj@example.com")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'email' && @.code == 'GIVEN_EMAIL_EXISTS')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenEmailFormatIsWrong() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
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
                .email("test.t")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'email' && @.code == 'INCORRECT_EMAIL_FORMAT')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenEmailIsBlank() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
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
                .email("")
                .location("Test")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'email' && @.code == 'EMAIL_NOT_BLANK')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenLocationIsBlank() throws Exception {
        //Given
        CarDataCommand carCommand = CarDataCommand.builder()
                .brand("test")
                .model("test")
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
                .location("")
                .carData(carCommand)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'location' && @.code == 'LOCATION_NOT_BLANK')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotSaveDriverWhenCarDataIsNull() throws Exception {
        //Given
        CreateDriverCommand command = CreateDriverCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t@example.com")
                .location("Test")
                .carData(null)
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(404))
                .andExpect(jsonPath("$.status").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Driver with id: 6 not found!"))
                .andExpect(jsonPath("$.uri").value("/api/drivers/6"))
                .andExpect(jsonPath("$.method").value("GET"));

        //Then
        String responseJson = postman.perform(post("/api/drivers")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'carData' && @.code == 'CAR_DATA_NOT_NULL')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotEditDriverPartiallyWhenEmailExists() throws Exception {
        //Given
        UpdateDriverPartiallyCommand command = UpdateDriverPartiallyCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("robert.reniro@example.com")
                .location("Test")
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
//                .andExpect(jsonPath("$.uuid").value("a5c8d2f9-2f4a-4b5d-9b5c-1af0e4f7a24e"))
                .andExpect(jsonPath("$.name").value("Ania"))
                .andExpect(jsonPath("$.sex").value("FEMALE"))
                .andExpect(jsonPath("$.location").value("Bolesława Chrobrego 6, 73-108 Kobylanka"));

        //Then
        String responseJson = postman.perform(patch("/api/drivers/2")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'email' && @.code == 'GIVEN_EMAIL_EXISTS')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }

    @Test
    void shouldNotEditDriverPartiallyWhenEmailFormatIsWrong() throws Exception {
        //Given
        UpdateDriverPartiallyCommand command = UpdateDriverPartiallyCommand.builder()
                .name("Test")
                .surname("Test")
                .dob(LocalDate.of(2000, 1, 1))
                .statusDriver(AVAILABLE)
                .sex(MALE)
                .email("test.t")
                .location("Test")
                .build();

        String json = objectMapper.writeValueAsString(command);

        //When
        postman.perform(get("/api/drivers/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
//                .andExpect(jsonPath("$.uuid").value("a5c8d2f9-2f4a-4b5d-9b5c-1af0e4f7a24e"))
                .andExpect(jsonPath("$.name").value("Ania"))
                .andExpect(jsonPath("$.sex").value("FEMALE"))
                .andExpect(jsonPath("$.location").value("Bolesława Chrobrego 6, 73-108 Kobylanka"));

        //Then
        String responseJson = postman.perform(patch("/api/drivers/2")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.[?(@.field == 'email' && @.code == 'INCORRECT_EMAIL_FORMAT')]").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<ValidationErrorDto> errors = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        assertEquals(1, errors.size());
    }
}