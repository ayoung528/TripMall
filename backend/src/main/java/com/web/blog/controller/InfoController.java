package com.web.blog.controller;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.web.blog.model.overview.Overview;
import com.web.blog.service.InfoService;
import com.web.blog.service.OverviewService;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/info")
public class InfoController {

    @Autowired
    InfoService infoService;

    @Autowired
    OverviewService overviewService;

    @GetMapping("/getInfo")
    @ApiOperation(value = "상세정보 가져오기")
    public Object findInfo(@RequestParam int contentid) throws URISyntaxException {
        Overview overview = overviewService.findAllByContentId(contentid);
        Map<String, Object> result = new HashMap<String, Object>();
        if(overview == null){
            return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
        }
        int contentTypeId = overview.getContentTypeId();
        Object info;
        if(contentTypeId == 12){//관광지
            info = infoService.findTourinfo(contentid);
        }else if(contentTypeId == 14){//문화
            info = infoService.findCultureinfo(contentid);
        }else if(contentTypeId == 15){//공연
            info = infoService.findFestivalinfo(contentid);
        }else if(contentTypeId == 25){//코스
            info = infoService.findCourseinfo(contentid);
        }else if(contentTypeId == 28){//레포츠
            info = infoService.findLeisureinfo(contentid);
        }else if(contentTypeId == 39){//음식점
            info = infoService.findFoodinfo(contentid);
        }else{
            info = "N";
        }
		result.put("overview", overview);
		result.put("info", info);
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
    }
}