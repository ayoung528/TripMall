package com.web.blog.controller.collaborated;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.web.blog.model.collaborated.Overviewbased;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.service.TouranddiningService;
import com.web.blog.service.collaborated.OverviewbasedService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/overviewbased")
public class OverviewbasedController {

    @Autowired
    OverviewbasedService overviewbasedService;
    
    @Autowired
    TouranddiningService touranddiningService;
    
    
    @GetMapping("/getOverviewbasedList")
    @ApiOperation(value = "개요기반 리스트 가져오기") // SWAGGER UI에 보이는 이름
    public Object getOverviewbasedList(@RequestParam int contentid) throws URISyntaxException {
        List<Overviewbased> overviewbasedList = new ArrayList<>();
        overviewbasedList = overviewbasedService.findAllBySelectedSpot(contentid);

        List<Touranddining> td = new ArrayList<>();
        for (int i = 0; i < overviewbasedList.size(); i++) {
            td.add(touranddiningService.findAllByContentId(overviewbasedList.get(i).getRecommendedSpot()));
        }
        
        
		return new ResponseEntity<>(td, HttpStatus.OK);
    }
    
}