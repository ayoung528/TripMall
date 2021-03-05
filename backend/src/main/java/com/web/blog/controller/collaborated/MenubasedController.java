package com.web.blog.controller.collaborated;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.web.blog.model.collaborated.Menubased;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.service.ReviewService;
import com.web.blog.service.TouranddiningService;
import com.web.blog.service.collaborated.MenubasedService;

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
@RequestMapping("/api/menubased")
public class MenubasedController {

    @Autowired
    MenubasedService menubasedService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    TouranddiningService touranddiningService;
    
    
    @GetMapping("/getMenubasedList")
    @ApiOperation(value = "메뉴기반 리스트 가져오기") // SWAGGER UI에 보이는 이름
    public Object getMenubasedList(@RequestParam int contentid) throws URISyntaxException {
        List<Menubased> menubasedList = new ArrayList<>();
        menubasedList = menubasedService.findAllBySelectedStore(contentid);

        List<Touranddining> td = new ArrayList<>();
        for (int i = 0; i < menubasedList.size(); i++) {
            td.add(touranddiningService.findAllByContentId(menubasedList.get(i).getRecommendedStore()));
        }
        
        
		return new ResponseEntity<>(td, HttpStatus.OK);
    }
    
}