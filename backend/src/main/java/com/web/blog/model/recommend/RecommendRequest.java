package com.web.blog.model.recommend;

import lombok.Data;

import javax.validation.Valid;

@Data
@Valid
public class RecommendRequest {
    int contentid;
    int contenttypeid;
    int subcontentid;
    String subdetailalt;
    String subdetailimg;
    String subdetailoverview;
    String subname;
    int subnum;
}
