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
@Table(name = "tourinfo") // 이어줄 테이블명 지정
public class Tourinfo {

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
    @Column(name = "guide")
    private String guide;
    @Column(name = "info_center")
    private String infoCenter;
    @Column(name = "open_date")
    private String openDate;
    @Column(name = "parking")
    private String parking;
    @Column(name = "rest_date")
    private String restDate;
    @Column(name = "season")
    private String season;
    @Column(name = "use_time")
    private String useTime;
    @Column(name = "cultural_heritage")
    private Boolean culturalHeritage;
    @Column(name = "natural_heritage")
    private Boolean naturalHeritage;
    @Column(name = "archival_heritage")
    private Boolean archivalHeritage;
}