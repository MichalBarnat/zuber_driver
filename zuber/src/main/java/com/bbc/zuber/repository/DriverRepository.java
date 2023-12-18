package com.bbc.zuber.repository;

import com.bbc.zuber.model.driver.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    @Query(value = "SELECT DISTINCT d FROM drivers d LEFT JOIN FETCH d.car")
    Page<Driver> findAllWithCar(Pageable pageable);

    boolean existsByEmail(String email);
}
