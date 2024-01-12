package com.bbc.zuber.model.car;

import com.bbc.zuber.model.car.enums.TypeOfCar;
import com.bbc.zuber.model.car.enums.TypeOfEngine;
import com.bbc.zuber.model.driver.Driver;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "cars")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE cars SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
public class Car {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "cars_seq")
    @SequenceGenerator(name = "cars_seq", sequenceName = "cars_seq", allocationSize = 1)
    private Long id;
    private UUID uuid;
    private String brand;
    private String model;
    private int productionYear;
    @Enumerated(STRING)
    private TypeOfEngine engine;
    @Enumerated(STRING)
    private TypeOfCar type;
    private int size;
    private String plateNum;
    @OneToOne
    @JoinColumn(name = "DRIVER_UUID", referencedColumnName = "uuid")
    @JsonIgnore
    private Driver driver;
    @Builder.Default
    private Boolean isDeleted = false;
}
