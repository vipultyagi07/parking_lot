package com.vip.controller;


import com.vip.entity.Vehicle;
import com.vip.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;


    @GetMapping("/get/all")
    public List<Vehicle> getAllVehicle(){

        return vehicleService.getAllVehicle();
    }


}
