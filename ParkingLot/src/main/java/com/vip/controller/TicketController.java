package com.vip.controller;

import com.vip.common.dto.EntranceTicketDto;
import com.vip.entity.Vehicle;
import com.vip.service.EntranceGateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private EntranceGateService entranceGateService;


    @PostMapping("/get/all/active")
    public ResponseEntity<Object> generateParkingTicket(@RequestBody Vehicle vehicle){
        EntranceTicketDto entranceTicketDto = entranceGateService.generateParkingTicket(vehicle);
        return new ResponseEntity<>(entranceTicketDto, HttpStatus.OK);
    }

}
