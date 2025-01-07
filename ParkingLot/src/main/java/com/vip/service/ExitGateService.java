package com.vip.service;

import com.vip.common.dto.ExitTicketDto;
import com.vip.entity.ParkingTicket;
import com.vip.entity.TransactionTable;
import com.vip.exception.ErrorCode;
import com.vip.exception.ParkingLotException;
import com.vip.repo.TransactionTableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Service
@Slf4j
public class ExitGateService {


    @Autowired
    private  ParkingTicketService parkingTicketService;

    @Autowired
    private TransactionTableRepository transactionTableRepository;

    public ExitTicketDto generatePayment(String vehicleNumber,Long userId) {
        try {
        ParkingTicket parkingTicket=parkingTicketService.findParkingTicketOfVehicle(vehicleNumber);
        if(Objects.isNull(parkingTicket)){
            throw new ParkingLotException("Vehicle "+vehicleNumber+" already left the parking lot ", ErrorCode.VEHICLE_ALREADY_LEFT_THE_PARKING_LOT);
        }

        LocalDateTime entryTime = parkingTicket.getEntryTime();
        LocalDateTime exitTime = parkingTicket.getExitTime();
        Duration duration = Duration.between(entryTime, exitTime);
        long totalTime = duration.toHours();


        Long price = parkingTicket.getParkingSpot().getPrice().longValue();

        ExitTicketDto exitTicketDto = getExitTicketDto(parkingTicket, entryTime, exitTime, totalTime, price);
        // make entry in Transaction Table

        saveTransactionData(parkingTicket, entryTime, exitTime, totalTime, price,userId);



        return exitTicketDto;
        } catch (ParkingLotException ex) {
            // Handle the custom exception, log the error, or perform other actions
            log.error("Parking Lot Exception: " + ex.getErrorCode() + " - " + ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Unexpected error: " + ex.getMessage(), ex);
            throw new ParkingLotException("Internal Server Error", ErrorCode.INTERNAL_SERVER_ERROR);
        }

    }

    private static ExitTicketDto getExitTicketDto(ParkingTicket parkingTicket, LocalDateTime entryTime, LocalDateTime exitTime, long totalTime, Long price) {
        ExitTicketDto exitTicketDto= new ExitTicketDto();
        exitTicketDto.setRate(price +"/hour");
        exitTicketDto.setDuration(totalTime);
        exitTicketDto.setParkingTicketId(parkingTicket.getId());
        exitTicketDto.setEntryTime(entryTime);
        exitTicketDto.setVehicleNumber(parkingTicket.getVehicle().getVehicleNo());
        exitTicketDto.setVehicletype(parkingTicket.getVehicle().getVehicleType().getDisplayName());
        exitTicketDto.setExitTime(exitTime);
        exitTicketDto.setAmountToBePaid(price * totalTime);
        return exitTicketDto;
    }

    private void saveTransactionData(ParkingTicket parkingTicket, LocalDateTime entryTime, LocalDateTime exitTime, long totalTime, Long price,Long userId) {

        TransactionTable transactionTable = new TransactionTable();
        transactionTable.setRate(price +"/hour");
        transactionTable.setDuration(totalTime);
        transactionTable.setCreatedDate(new Date());
        transactionTable.setUserId(userId);
        transactionTable.setEntryTime(entryTime);
        transactionTable.setParkingTicketId(parkingTicket.getId());
        transactionTable.setVehicleNumber(parkingTicket.getVehicle().getVehicleNo());
        transactionTable.setVehicletype(parkingTicket.getVehicle().getVehicleType());
        transactionTable.setExitTime(exitTime);
        transactionTable.setAmountToBePaid(price * totalTime);
        transactionTableRepository.save(transactionTable);
        log.info("saveTransactionData: saved transaction data for vehicleNo: {} by user: {}",parkingTicket.getVehicle().getVehicleNo(),userId);

    }
}
