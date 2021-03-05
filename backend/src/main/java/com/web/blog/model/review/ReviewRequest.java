package com.web.blog.model.review;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

@Data
@Valid
public class ReviewRequest {
    @ApiModelProperty(required = true)
    private int uid;
    @ApiModelProperty(required = true)
    private String nickname;
    @ApiModelProperty(required = true)
    private int contentId;
    @ApiModelProperty(required = true)
    @Max(5)
    private int score;
    @Size(max = 50)
    private String comment;
}
