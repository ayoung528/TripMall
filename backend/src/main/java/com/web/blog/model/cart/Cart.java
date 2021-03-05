package com.web.blog.model.cart;

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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "cart") // 이어줄 테이블명 지정
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;
    @Column(name = "userid")
    private int userid;
    @Column(name = "content_id")
    private int contentId;
    @Column(name ="content_type_id")
    private int contentTypeId;
    @Column(name = "latitude")
    private float latitude;
    @Column(name = "longitude")
    private float longitude;
    @Column(name = "areacode")
    private int areacode;
    @Column(name = "title")
    private String title;
    
}