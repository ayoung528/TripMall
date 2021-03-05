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
@Table(name = "courseinfo") // 이어줄 테이블명 지정
public class Courseinfo {

    @Id
    private int contentId;
    @Column(name = "distance")
    private String distance;
    @Column(name = "info_center")
    private String infoCenter;
    @Column(name ="schedule")
    private String schedule;
    @Column(name = "spend_time")
    private String spendTime;
    @Column(name = "theme")
    private String theme;
}