package com.vip.dto;

import com.vip.common.enums.VehicleType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingTicketInfoDTO {
    private Long ticketId;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private Long vehicleId;
    private String vehicleNo;
    private VehicleType vehicleType;
    private Long parkingSpotId;
    private boolean isEmpty;
    private String spotType;
    private BigDecimal price;

    // Default constructor
    public ParkingTicketInfoDTO() {
    }

    // Getters and Setters
    // Ensure all fields have appropriate getters and setters
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getSpotType() {
        return spotType;
    }

    public void setSpotType(String spotType) {
        this.spotType = spotType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ParkingTicketInfoDTO(Long ticketId, LocalDateTime entryTime, LocalDateTime exitTime, Long vehicleId, String vehicleNo, VehicleType vehicleType, Long parkingSpotId, boolean isEmpty, String spotType, BigDecimal price) {
        this.ticketId = ticketId;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.vehicleId = vehicleId;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.parkingSpotId = parkingSpotId;
        this.isEmpty = isEmpty;
        this.spotType = spotType;
        this.price = price;
    }
}
