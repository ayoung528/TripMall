package com.web.blog.model.recommend;

import io.swagger.annotations.ApiModelProperty;

public class RecommendResponse {
    // recommend
    @ApiModelProperty(value = "contentid", position = 1)
    public int contentid;
    @ApiModelProperty(value = "contenttypeid", position = 2)
    public int contenttypeid;
    @ApiModelProperty(value = "subcontentid", position = 3)
    public int subcontentid;
    @ApiModelProperty(value = "subdetailalt", position = 4)
    public String subdetailalt;
    @ApiModelProperty(value = "subdetailimg", position = 5)
    public String subdetailimg;
    @ApiModelProperty(value = "subdetailoverview", position = 6)
    public String subdetailoverview;
    @ApiModelProperty(value = "subname", position = 7)
    public String subname;
    @ApiModelProperty(value = "subnum", position = 8)
    public int subnum;
}