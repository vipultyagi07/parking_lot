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
        long countByType = parkingSpotRepository.countByType(vehicleType.getDisplayName());
        long countByTypeAndIsEmptyTrue = parkingSpotRepository.countByTypeAndIsEmptyTrue(vehicleType.getDisplayName());

        parkingSpotDto.setTotalSpot(countByType);
        parkingSpotDto.setFreeSpot(countByTypeAndIsEmptyTrue);
        parkingSpotDto.setVehicleType(vehicleType);
        return parkingSpotDto;
    }
}
