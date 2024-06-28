package com.vip.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailDetails {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;

}
