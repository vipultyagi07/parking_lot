package com.vip.service;

import com.vip.entity.Test;
import com.vip.repo.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;
    public ResponseEntity<Test> saveDataToDB(Test test) {

        Test save = testRepository.save(test);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }

    public List<Map<String, Object>> getLogsByType(int logIdParam, String logTypeParam) {
        return testRepository.getLogsByType(logIdParam, logTypeParam);

    }
}
