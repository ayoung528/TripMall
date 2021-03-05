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
@Table(name = "cultureinfo") // 이어줄 테이블명 지정
public class Cultureinfo {

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
    @Column(name = "discount_info")
    private String discountInfo;
    @Column(name = "info_center")
    private String infoCenter;
    @Column(name = "parking")
    private String parking;
    @Column(name = "parking_fee")
    private String parkingFee;
    @Column(name = "rest_date")
    private String restDate;
    @Column(name = "use_fee")
    private String useFee;
    @Column(name = "use_time")
    private String useTime;
    @Column(name = "scale")
    private String scale;
    @Column(name = "spend_time")
    private String spendTime;
}