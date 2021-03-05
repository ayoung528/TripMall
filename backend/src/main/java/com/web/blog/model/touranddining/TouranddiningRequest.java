package com.web.blog.model.touranddining;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

import javax.validation.Valid;


@Data
@Valid
class TouranddiningRequest {
    int contentid;
    int contenttypeid;
    int subcontentid;
    String subdetailalt;
    String subdetailimg;
    String subdetailoverview;
    String subname;
    int subnum;
}
