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
@Table(name = "foodinfo") // 이어줄 테이블명 지정
public class Foodinfo {

    @Id
    private int fid;
    @Column(name = "content_id")
    private int contentId;
    @Column(name = "discountinfofood")
    private String discountinfofood;
    @Column(name = "lcnsno")
    private Long lcnsno;
    @Column(name ="credit_card")
    private String creditCard;
    @Column(name = "discount_info")
    private String discountInfo;
    @Column(name = "rep_menu")
    private String repMenu;
    @Column(name = "info_center")
    private String infoCenter;
    @Column(name = "kids_facility")
    private Boolean kidsFacility;
    @Column(name = "open_date")
    private String openDate;
    @Column(name = "open_time")
    private String openTime;
    @Column(name = "packing")
    private String packing;
    @Column(name = "parking")
    private String parking;
    @Column(name = "reservation_info")
    private String reservationInfo;
    @Column(name = "rest_date")
    private String restDate;
    @Column(name = "scale")
    private String scale;
    @Column(name = "seat")
    private String seat;
    @Column(name = "smoking")
    private String smoking;
    @Column(name = "treat_menus")
    private String treatMenus;
}