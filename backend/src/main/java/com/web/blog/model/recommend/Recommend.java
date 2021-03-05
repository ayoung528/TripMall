package com.web.blog.model.recommend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.web.blog.model.touranddining.Touranddining;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "recommend") // 이어줄 테이블명 지정
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;

    // @OneToOne(fetch = FetchType.LAZY)
    // private Touranddining touranddining;
    @Column(name = "contentid")
    private int contentid;
    @Column(name = "contenttypeid")
    private int contenttypeid;
    @Column(name = "subcontentid")
    private int subcontentid;
    @Column(name = "subdetailalt")
    private String subdetailalt;
    @Column(name ="subdetailimg")
    private String subdetailimg;
    @Column(name = "subdetailoverview")
    private String subdetailoverview;
    @Column(name = "subname")
    private String subname;
    @Column(name = "subnum")
    private int subnum;
}