package com.web.blog.controller.collaborated;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.web.blog.dao.UserDao;
import com.web.blog.model.collaborated.Overviewbased;
import com.web.blog.model.collaborated.Usersimilaritybased;
import com.web.blog.model.route.Route;
import com.web.blog.model.route.RouteDetail;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;
import com.web.blog.service.RouteDetailService;
import com.web.blog.service.RouteService;
import com.web.blog.service.TouranddiningService;
import com.web.blog.service.collaborated.OverviewbasedService;
import com.web.blog.service.collaborated.UsersimilaritybasedService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/usersimilaritybased")
public class UsersimilaritybasedController {

    @Autowired
    UsersimilaritybasedService usersimilaritybasedService;
    
    @Autowired
    RouteService routeService;
    @Autowired
    RouteDetailService routeDetailService;
    @Autowired
    TouranddiningService touranddiningService;

    @Autowired
    JwtService jwtService;
    @Autowired
    UserDao userDao;
    @GetMapping("/getUsersimilaritybasedList")
    @ApiOperation(value = "유저기반 루트 가져오기") // SWAGGER UI에 보이는 이름
    public Object getUsersimilaritybasedList(@RequestHeader String token) throws URISyntaxException {
        User user = jwtService.getUser(token);
        
        String email = user.getEmail();
        List<Usersimilaritybased> usersimilaritybasedList = new ArrayList<>();
        usersimilaritybasedList = usersimilaritybasedService.findAllBySelectedUser(email);
         
        List<Route> route = new ArrayList<>();
        List<Route> recommend = new ArrayList<>();
        for (int i = 0; i < usersimilaritybasedList.size(); i++) {
            String userEmail = usersimilaritybasedList.get(i).getRecommendedUser();
            User users = userDao.getUserByEmail(userEmail);
            
            route = routeService.findAllByUserid(users.getId());
            for (int j = 0; j < route.size(); j++) {
                recommend.add(route.get(j));
            }
        }
        Set<Touranddining> td = new HashSet<>();
        for(Route r : recommend){
            List<RouteDetail> routedetails = routeDetailService.findAllByRouteid(r.getRouteid());
            for(RouteDetail rd : routedetails){
                td.add(touranddiningService.findAllByContentId(rd.getSubcontentid()));
            }
        }
		return new ResponseEntity<>(td, HttpStatus.OK);
    }
    
}