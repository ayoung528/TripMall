package com.web.blog.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

import javax.validation.Valid;

@Data
@Valid
public class UserRequest {
    String token;
    String email;
    String nickname;
    String profileImage;
    String gender;
    String ageRange;
}
