package com.bbc.zuber.model.driver;

import com.bbc.zuber.model.car.Car;
import com.bbc.zuber.model.driver.enums.Sex;
import com.bbc.zuber.model.driver.enums.StatusDriver;
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

import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "drivers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE drivers SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted = false")
public class Driver {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "driver_seq")
    @SequenceGenerator(name = "driver_seq", sequenceName = "drivers_seq", allocationSize = 1)
    private Long id;
    private UUID uuid;
    private String name;
    private String surname;
    private LocalDate dob;
    @Enumerated(STRING)
    private StatusDriver statusDriver;
    @Enumerated(STRING)
    private Sex sex;
    private String email;
    private String location;
    @OneToOne(cascade = {PERSIST, MERGE, REMOVE})
    @JoinColumn(name = "CAR_UUID", referencedColumnName = "uuid")
    private Car car;
    @Builder.Default
    private Boolean isDeleted = false;
}