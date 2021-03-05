package com.web.blog.controller.collaborated;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.web.blog.model.collaborated.Distancebased;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.service.TouranddiningService;
import com.web.blog.service.collaborated.DistancebasedService;

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
@RequestMapping("/api/distancebased")
public class DistancebasedController {

    @Autowired
    DistancebasedService distancebasedService;
    
    @Autowired
    TouranddiningService touranddiningService;
    
    
    @GetMapping("/getDistancebasedList")
    @ApiOperation(value = "거리기반 리스트 가져오기") // SWAGGER UI에 보이는 이름
    public Object getDistancebasedList(@RequestParam int contentid) throws URISyntaxException {
        List<Distancebased> distancebasedList = new ArrayList<>();
        distancebasedList = distancebasedService.findAllBySelectedSpot(contentid);

        List<Touranddining> td = new ArrayList<>();
        for (int i = 0; i < distancebasedList.size(); i++) {
            td.add(touranddiningService.findAllByContentId(distancebasedList.get(i).getNearSpot()));
        }
        
        
		return new ResponseEntity<>(td, HttpStatus.OK);
    }
    
}