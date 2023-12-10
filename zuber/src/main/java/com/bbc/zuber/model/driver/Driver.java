package com.bbc.zuber.model.driver;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "drivers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private UUID uuid;
    private String name;
    private String surname;
    private String dob;
    @Enumerated(EnumType.STRING)
    private StatusDriver statusDriver;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    @Email(message = "Wrong email pattern. Check it once again!")
    private String email;
    private String location;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CAR_UUID", referencedColumnName = "uuid")
    private Car car;
}