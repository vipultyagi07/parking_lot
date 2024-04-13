package com.vip.controller;

import com.vip.common.dto.ExitTicketDto;
import com.vip.entity.Test;
import com.vip.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")

public class TestController {

    @Autowired
    private TestService testService;


    @PostMapping("/db")
    public ResponseEntity<Test> saveDataToDB(@RequestBody Test test ){

        return testService.saveDataToDB(test);
    }

    @GetMapping("/getLogs")
    public List<Map<String, Object>> getLogsByType(@RequestParam("logId") int logIdParam, @RequestParam("logType") String logTypeParam
    ) {
        return testService.getLogsByType(logIdParam, logTypeParam);
    }
}
