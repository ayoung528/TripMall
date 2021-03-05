package com.web.blog.model.review;

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
@Table(name = "review") // 이어줄 테이블명 지정
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rid;
    @Column(name = "uid")
    private int uid;
    @Column(name = "content_id")
    private int contentId;
    @Column(name ="score")
    private int score;
    @Column(name = "comment")
    private String comment;
    @Column(name = "write_time", insertable = false, updatable = false)
    private LocalDateTime writeTime;
    @Column(name = "nickname")
    private String nickname;
}