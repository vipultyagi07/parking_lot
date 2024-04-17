package com.vip.controller;

import com.vip.common.dto.EntranceTicketDto;
import com.vip.common.dto.ParkingSpotDto;
import com.vip.common.enums.VehicleType;
import com.vip.entity.Vehicle;
import com.vip.service.ParkingSpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-spot")

public class ParkingSpotController {

    @Autowired
    private ParkingSpotService parkingSpotService;

    @GetMapping("/free/spot/{vehicleType}")
    public ResponseEntity<Object> viewParkingSpot(@PathVariable VehicleType vehicleType){
        ParkingSpotDto parkingSpot = parkingSpotService.viewParkingSpot(vehicleType);
        return new ResponseEntity<>(parkingSpot, HttpStatus.OK);
    }

}
