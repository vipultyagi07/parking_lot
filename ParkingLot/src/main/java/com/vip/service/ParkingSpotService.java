package com.vip.service;

import com.vip.common.dto.ParkingSpotDto;
import com.vip.common.enums.VehicleType;
import com.vip.entity.ParkingSpot;
import com.vip.repo.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository parkingSpotRepository;

    public ParkingSpot findParkingSpace(VehicleType vehicleType) {
        String type = vehicleType.getDisplayName();
        List<ParkingSpot> parkingSpot=parkingSpotRepository.findByTypeAndIsEmptyTrue(type);
        return parkingSpot.size()==0?null:parkingSpot.get(0);
    }

    public void updateParkingSpace(ParkingSpot parkingSpot, boolean isEmpty) {
        parkingSpot.setEmpty(isEmpty);
        parkingSpotRepository.save(parkingSpot);
    }

    public ParkingSpotDto viewParkingSpot(VehicleType vehicleType) {
        ParkingSpotDto parkingSpotDto= new ParkingSpotDto();
        Object[] result = parkingSpotRepository.countTotalAndFreeSpots(vehicleType.getDisplayName());

        // Extract total and free spot counts from the result
        long totalSpot = (result != null && result.length > 0 && result[0] != null) ? (long) result[0] : 0;
        long freeSpot = (result != null && result.length > 1 && result[1] != null) ? (long) result[1] : 0;

        parkingSpotDto.setTotalSpot(totalSpot);
        parkingSpotDto.setFreeSpot(freeSpot);
        parkingSpotDto.setVehicleType(vehicleType);
        return parkingSpotDto;
    }
}
