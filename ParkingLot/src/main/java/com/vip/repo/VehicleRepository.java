package com.vip.repo;

import com.vip.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {
    Vehicle findByVehicleNo(String vehicleNo);
//    @Query("SELECT * FROM  parking_lot.vehicle v WHERE v.lastUpdatedTime > :lastCheckedTime")
//    List<Vehicle> findUpdatedVehicles(@Param("lastCheckedTime") LocalDateTime lastCheckedTime);

//    @Query(value = "SELECT * FROM parking_lot.vehicle WHERE DATE(created_date) > :createdDate", nativeQuery = true)
//    List<Vehicle> findUpdatedVehicles(@Param("createdDate") LocalDateTime createdDate);


    @Query(value = "SELECT * FROM parking_lot.vehicle WHERE created_date > :createdDate", nativeQuery = true)
    List<Vehicle> findUpdatedVehicles(@Param("createdDate") Date createdDate);
}
