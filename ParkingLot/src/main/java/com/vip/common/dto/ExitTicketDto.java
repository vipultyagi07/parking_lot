package com.vip.common.dto;

import com.vip.common.enums.VehicleType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class ExitTicketDto {

    private String vehicleNumber;
    private Long parkingTicketId;
    private String vehicletype;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Long duration;
    private String rate;
    private Long amountToBePaid;



}
