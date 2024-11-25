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
@Table(name="state")
public class State extends BaseEntity {



    @Column(name = "state_id",unique = true)
    private long stateId;

    private String name;
    private boolean showInWebsite;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;
}
