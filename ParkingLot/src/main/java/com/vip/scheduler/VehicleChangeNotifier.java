package com.vip.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vip.entity.Vehicle;
import com.vip.service.VehicleService;
import com.vip.websocket.VehicleWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class VehicleChangeNotifier {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleWebSocketHandler webSocketHandler;

    private LocalDateTime lastCheckedTime = LocalDateTime.now();

    @Scheduled(fixedRate = 10000)
    public void checkForChanges() {

        Date lastCheckedDate = Date.from(lastCheckedTime.atZone(ZoneId.systemDefault()).toInstant());

        List<Vehicle> updatedVehicles = vehicleService.getUpdatedVehicles(lastCheckedDate);

        if (!updatedVehicles.isEmpty()) {
            notifyClients(vehicleService.getAllVehicle());
            lastCheckedTime = LocalDateTime.now(); // Update last checked time
        }
    }

    private void notifyClients(List<Vehicle> vehicles) {
        try {
            String message = new ObjectMapper().writeValueAsString(vehicles);
            webSocketHandler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
