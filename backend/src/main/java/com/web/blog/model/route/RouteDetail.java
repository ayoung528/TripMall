package com.web.blog.model.route;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "routedetail") // 이어줄 테이블명 지정
public class RouteDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rdid;
    @Column(name = "routeid")
    private int routeid;
    @Column(name = "subnum")
    private int subnum;
    @Column(name = "subtitle")
    private String subtitle;
    @Column(name = "subimg")
    private String subimg;
    @Column(name ="suboverview")
    private String suboverview;
    @Column(name = "subcontentid")
    private int subcontentid;
    @Column(name = "subcontenttypeid")
    private int subcontenttype;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
    @Column(name = "areacode")
    private int areacode;
    @Column(name = "review")
    private boolean review;
}