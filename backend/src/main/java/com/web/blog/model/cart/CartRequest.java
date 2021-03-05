package com.web.blog.model.cart;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

@Data
@Valid
public class CartRequest {
    @ApiModelProperty(required = true)
    private int cid;
    @ApiModelProperty(required = true)
    private int userid;
    @ApiModelProperty(required = true)
    private int contentId;
    @ApiModelProperty(required = true)
    private int contentTypeId;
    private float latitude;
    private float longitude;
    private int areacode;
    private String title;
}
