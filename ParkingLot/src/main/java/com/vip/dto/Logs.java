package com.vip.dto;

import lombok.Data;

import java.util.Date;
@Data
public class Logs {

    private Long Id;
    private String logType;
    private String logMessage;
    private Date currentDate;

}
