package com.bbc.zuber.repository;

import com.bbc.zuber.model.driver.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}