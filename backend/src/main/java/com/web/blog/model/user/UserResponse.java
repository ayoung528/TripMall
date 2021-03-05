package com.web.blog.model.user;

import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class UserResponse {
    // user

    @ApiModelProperty(value = "id", position = 1)
    public int id;
    @ApiModelProperty(value = "email", position = 3)
    public String email;
    @ApiModelProperty(value = "nickname", position = 5)
    public String nickname;
    @ApiModelProperty(value = "profileImage", position = 6)
    public String profileImage;
    @ApiModelProperty(value = "gender", position = 7)
    public String gender;
    @ApiModelProperty(value = "ageRange", position = 7)
    public String ageRange;
}