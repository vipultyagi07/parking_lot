package com.vip.entity;

import com.vip.entity.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="city")
public class City extends BaseEntity {



    @Column(name = "city_id",unique = true)
    private long cityId;

    private String name;
    private boolean showInWebsite;


    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    private State state;


}
