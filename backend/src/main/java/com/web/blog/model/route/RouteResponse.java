package com.web.blog.model.route;

import io.swagger.annotations.ApiModelProperty;

public class RouteResponse {
    // recommend
    @ApiModelProperty(value = "userId", position = 1)
    public int userId;
    @ApiModelProperty(value = "areacode", position = 2)
    public int areacode;
    @ApiModelProperty(value = "title", position = 4)
    public String title;
    @ApiModelProperty(value = "readcount", position = 5)
    public int readcount;
    @ApiModelProperty(value = "subcontentid", position = 6)
    public int subcontentid;
    @ApiModelProperty(value = "subdetailimg", position = 7)
    public String subdetailimg;
    @ApiModelProperty(value = "subdetailoverview", position = 8)
    public String subdetailoverview;
    @ApiModelProperty(value = "subname", position = 9)
    public String subname;
    @ApiModelProperty(value = "subnum", position = 10)
    public int subnum;
}