package com.vip.repo;

import com.vip.dto.ParkingTicketInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface CustomParkingTicketRepository {
    Page<ParkingTicketInfoDTO> findParkingTicketInfoByUserId(Long userId, Pageable pageable, Sort sort,String vehicleNumber);
}
