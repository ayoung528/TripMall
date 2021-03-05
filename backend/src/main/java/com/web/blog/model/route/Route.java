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
@Table(name = "route") // 이어줄 테이블명 지정
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int routeid;
    @Column(name = "userid")
    private int userid;
    @Column(name = "areacode")
    private int areacode;
    @Column(name = "title")
    private String title;
    @Column(name = "thumbnail")
    private String thumbnail;
    @Column(name ="readcount")
    private int readcount;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
}