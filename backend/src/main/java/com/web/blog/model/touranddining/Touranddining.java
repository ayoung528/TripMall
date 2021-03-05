package com.web.blog.model.touranddining;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.blog.model.overview.Overview;
import com.web.blog.model.recommend.Recommend;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "touranddining") // 이어줄 테이블명 지정
public class Touranddining {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contentId;
    @Column(name = "addr1")
    private String addr1;
    @Column(name = "addr2")
    private String addr2;
    @Column(name = "areacode")
    private int areacode;
    @Column(name = "cat1")
    private String cat1;
    @Column(name ="cat2")
    private String cat2;
    @Column(name = "cat3")
    private String cat3;
    @Column(name = "contentTypeId")
    private int contentTypeId;
    @Column(name = "firstImage")
    private String firstImage;
    @Column(name = "firstImage2")
    private String firstImage2;
    @Column(name = "mapx")
    private float mapx;
    @Column(name = "mapy")
    private float mapy;
    @Column(name = "sigungucode")
    private int sigungucode;
    @Column(name = "tel")
    private String tel;
    @Column(name = "title")
    private String title;
    @Column(name = "readcount")
    private int readcount;
    @Column(name = "score")
    private double score;
    // @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "touranddining")
    // @JoinColumn(name = "content_id")
    // private Overview overview;

    // @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "touranddining")
    // @JoinColumn(name = "content_id")
    // private Recommend recommend;
}