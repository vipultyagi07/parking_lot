package com.vip.repo;

import com.vip.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot,Long> {
    long countByType(String type);
    List<ParkingSpot> findByType(String type);

    List<ParkingSpot> findByTypeAndIsEmptyTrue(String type);

    @Query("SELECT COUNT(p), SUM(CASE WHEN p.isEmpty = true THEN 1 ELSE 0 END) FROM ParkingSpot p WHERE p.type = :vehicleType")
    Object[] countTotalAndFreeSpots(@Param("vehicleType") String vehicleType);}
