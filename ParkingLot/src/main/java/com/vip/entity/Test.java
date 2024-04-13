package com.vip.entity;

import com.vip.dto.Logs;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = LogDtoConvertor.class)
    private List<Logs> logs;
}
