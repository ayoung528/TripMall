package com.web.blog.model.collaborated;

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
@Table(name = "surveyresult") // 이어줄 테이블명 지정
public class Surveyresult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;
    @Column(name = "content_id")
    private int contentId;
    @Column(name = "uid")
    private int uid;
    @Column(name ="score")
    private float score;
}