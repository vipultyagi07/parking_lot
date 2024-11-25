package com.vip.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "pin_code")
@Data
@NoArgsConstructor
public class PinCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "office_name")
    private String officename;

    @Column(name = "pin_code")
    private int pincode;

    @Column(name = "office_type")
    private String officeType;

    @Column(name = "delivery_status")
    private String deliverystatus;

    @Column(name = "division_name")
    private String divisionname;

    @Column(name = "region_name")
    private String regionname;

    @Column(name = "circle_name")
    private String circlename;

    @Column(name = "taluk")
    private String taluk;

    @Column(name = "district_name")
    private String districtname;

    @Column(name = "state_name")
    private String statename;

    @Column(name = "country_name")
    private String countryname;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "related_sub_office")
    private String relatedSuboffice;

    @Column(name = "related_head_office")
    private String relatedHeadoffice;

    @ManyToOne
    @JoinColumn(name = "state_id", referencedColumnName = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;
}
