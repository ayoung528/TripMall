package com.web.blog.model.user;

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
@Table(name = "user") // 이어줄 테이블명 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "nickname")
    private String nickname;
    @Column(name ="profile_image")
    private String profileImage;
    @Column(name = "gender")
    private String gender;
    @Column(name = "ageRange")
    private String ageRange;
    @Column(insertable = false, updatable = false)
    private LocalDateTime createTime;

    public User(String email, String nickname){
        this.email= email;
        this.nickname=nickname;
    }

    public User(int id, String email, String nickname){
        this.id = id;
        this.email= email;
        this.nickname=nickname;
    }

    public User(String email, String nickname, String profileImage, String gender, String ageRange){
        this.email= email;
        this.nickname=nickname;
        this.profileImage=profileImage;
        this.gender=gender;
        this.ageRange=ageRange;
    }

}