package com.vip.common.dto;

import com.vip.common.enums.VehicleType;
import lombok.Data;

@Data
public class ParkingSpotDto {
    private Long totalSpot;
    private VehicleType vehicleType;
    private Long freeSpot;
}
