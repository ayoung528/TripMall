package com.web.blog.model.route;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

import javax.validation.Valid;

@Data
@Valid
public class RouteRequest {
    int routeid;
    int userid;
    String title;
    int[] contentid;
}
