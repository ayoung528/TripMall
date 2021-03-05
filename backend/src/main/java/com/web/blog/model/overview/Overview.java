package com.web.blog.model.overview;

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
@Table(name = "overview") // 이어줄 테이블명 지정
public class Overview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;

    @Column(name ="content_id")
    private int contentId;

    @Column(name ="content_type_id")
    private int contentTypeId;
    @Column(name = "overview")
    private String overview;
    @Column(name = "homepage")
    private String homepage;
}