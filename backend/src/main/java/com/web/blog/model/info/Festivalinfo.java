package com.web.blog.model.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

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
@Table(name = "festivalinfo") // 이어줄 테이블명 지정
public class Festivalinfo {

    @Id
    private int contentId;
    @Column(name = "discountinfofestival")
    private String discountinfofestival;
    @Column(name = "playtime")
    private String playtime;
    @Column(name ="program")
    private String program;
    @Column(name = "sponsor1tel")
    private String sponsor1tel;
    @Column(name = "sponsor2tel")
    private String sponsor2tel;
    @Column(name = "age_limit")
    private String ageLimit;
    @Column(name = "reservation_place")
    private String reservationPlace;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "place")
    private String place;
    @Column(name = "festival_grade")
    private String festivalGrade;
    @Column(name = "place_guide")
    private String placeGuide;
    @Column(name = "spend_time")
    private String spendTime;
    @Column(name = "organizer")
    private String organizer;
    @Column(name = "host")
    private String host;
    @Column(name = "sub_event")
    private String subEvent;
    @Column(name = "use_fee")
    private String useFee;
}