package com.vip.service;

import com.vip.entity.Vehicle;
import com.vip.repo.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    public Vehicle findExistingVehicle(Vehicle vehicle) {
        Vehicle oldVehicle=vehicleRepository.findByVehicleNo(vehicle.getVehicleNo());
        return oldVehicle!=null? oldVehicle:vehicle;

    }

    public void save(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public Vehicle findExistingVehicle(String vehicleNumber) {
        Vehicle vehicle = vehicleRepository.findByVehicleNo(vehicleNumber);
        return vehicle;
    }

    public List<Vehicle> getAllVehicle() {

        List<Vehicle> allVehicle = vehicleRepository.findAll();
        return allVehicle;
    }

    public List<Vehicle> getUpdatedVehicles(Date lastCheckedTime) {
        return vehicleRepository.findUpdatedVehicles(lastCheckedTime);
    }
}
