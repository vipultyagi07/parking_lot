package com.vip.controller;

import com.vip.common.dto.ExitTicketDto;
import com.vip.entity.ParkingTicket;
import com.vip.entity.Vehicle;
import com.vip.exception.ParkingLotException;
import com.vip.security.JwtUtil;
import com.vip.service.EntranceGateService;
import com.vip.service.ExitGateService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/exit")
public class ExitController {

    @Autowired
    private ExitGateService exitGateService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/payment")
    public ResponseEntity<Object> generatePayment(@RequestParam String vehicleNumber, HttpServletRequest request){

        Long userId = jwtUtil.getUserIdFromHttpServletRequest(request);

        ExitTicketDto exitTicketDto = exitGateService.generatePayment(vehicleNumber,userId);
            return new ResponseEntity<>(exitTicketDto, HttpStatus.OK);

    }

}