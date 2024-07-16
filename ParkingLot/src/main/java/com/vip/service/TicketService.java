package com.vip.service;

import com.vip.dto.ParkingTicketInfoDTO;
import com.vip.entity.TransactionTable;
import com.vip.repo.ParkingTicketRepository;
import com.vip.repo.TransactionTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TicketService {


    @Autowired
    private ParkingTicketRepository parkingTicketRepository;
    @Autowired
    private TransactionTableRepository transactionTableRepository;

    public Page<ParkingTicketInfoDTO> getParkingTicketInfoByUserId(Long userId, Pageable pageable, Sort sort,String vehicleNumber) {
        return parkingTicketRepository.findParkingTicketInfoByUserId(userId, pageable,sort,vehicleNumber);
    }


    public TransactionTable getTransactionInfo(Long userId, Long ticketId) {

        TransactionTable transactionTable = transactionTableRepository.findByParkingTicketIdAndUserId(ticketId, userId);
        if(Objects.nonNull(transactionTable)){
            return transactionTable;
        }
        return null;
    }
}
