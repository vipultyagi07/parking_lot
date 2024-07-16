package com.vip.controller;


import com.vip.dto.ParkingTicketInfoDTO;
import com.vip.entity.TransactionTable;
import com.vip.security.JwtUtil;
import com.vip.service.ParkingTicketService;
import com.vip.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-tickets")
public class TicketController {
    @Autowired
    private TicketService parkingTicketService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/all/info")
    public Page<ParkingTicketInfoDTO> getParkingTicketInfoByUserId(
            Pageable pageable, @RequestParam String vehicleNumber, HttpServletRequest request) {
        Long userId = jwtUtil.getUserIdFromHttpServletRequest(request);
        Sort sort = pageable.getSort();
        return parkingTicketService.getParkingTicketInfoByUserId(userId, pageable, sort,vehicleNumber);
    }

    @GetMapping("/view-details/{ticketId}")
    public TransactionTable getTransactionInfo(@PathVariable Long ticketId, HttpServletRequest request ) {
        Long userId = jwtUtil.getUserIdFromHttpServletRequest(request);
        return parkingTicketService.getTransactionInfo(userId,ticketId);
    }


}
