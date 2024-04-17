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
    long countByTypeAndIsEmptyTrue(String type);

}
