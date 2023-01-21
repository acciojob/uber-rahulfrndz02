package com.driver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.driver.model.Driver;
@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer>{

    @Query(value = "DELETE FROM driver d WHERE d.driver_id = ?1 ",
            nativeQuery = true)
    void delete(int driverId);

    List<Driver> findAllByOrderByDriverId();
}
