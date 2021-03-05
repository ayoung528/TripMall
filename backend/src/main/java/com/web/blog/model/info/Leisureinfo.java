package com.web.blog.model.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "leisureinfo") // 이어줄 테이블명 지정
public class Leisureinfo {

    @Id
    private int contentId;
    @Column(name = "capacity")
    private String capacity;
    @Column(name = "baby_carriage")
    private String babyCarriage;
    @Column(name ="credit_card")
    private String creditCard;
    @Column(name = "pet")
    private String pet;
    @Column(name = "age_range")
    private String ageRange;
    @Column(name = "info_center")
    private String infoCenter;
    @Column(name = "open_period")
    private String openPeriod;
    @Column(name = "parking")
    private String parking;
    @Column(name = "parking_fee")
    private String parkingFee;
    @Column(name = "reservation_info")
    private String reservationInfo;
    @Column(name = "rest_date")
    private String restDate;
    @Column(name = "scale")
    private String scale;
    @Column(name = "use_time")
    private String useTime;
    @Column(name = "use_fee")
    private String useFee;
}