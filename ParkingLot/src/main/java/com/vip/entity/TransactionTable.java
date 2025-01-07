package com.vip.entity;

import com.vip.common.enums.VehicleType;
import com.vip.entity.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class TransactionTable extends BaseEntity {

    private String vehicleNumber;
    private Long parkingTicketId;

    private VehicleType vehicletype;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Long duration;
    private String rate;
    private Long amountToBePaid;
    private boolean paymentReceived;
    private Date paymentReceivedDate;
    private Long userId;

}
