package com.web.blog.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.web.blog.dao.CartDao;
import com.web.blog.model.cart.Cart;
import com.web.blog.model.overview.Overview;
import com.web.blog.model.recommend.Recommend;
import com.web.blog.model.route.Route;
import com.web.blog.model.route.RouteDetail;
import com.web.blog.model.route.RouteRequest;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.model.user.KsignupRequest;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.model.user.UserRequest;
import com.web.blog.model.user.UserResponse;
import com.web.blog.service.CartService;
import com.web.blog.service.JwtService;
import com.web.blog.service.KakaoService;
import com.web.blog.service.KakaoUserInfo;
import com.web.blog.service.OverviewService;
import com.web.blog.service.RecommendService;
import com.web.blog.service.RouteDetailService;
import com.web.blog.service.RouteService;
import com.web.blog.service.TouranddiningService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = UserResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = UserResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = UserResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    TouranddiningService touranddiningService;
    @Autowired
    RecommendService recommendService;
    @Autowired
    OverviewService overviewService;
    @Autowired
    RouteService routeService;
    @Autowired
    RouteDetailService routeDetailService;
    @Autowired
    CartService cartService;

    @PostMapping("/createroute")
    @ApiOperation(value = "?????? ?????? ??????") // SWAGGER UI??? ????????? ??????
    public ResponseEntity<?> createroute(@RequestBody RouteRequest req) throws URISyntaxException {
        String title = req.getTitle();
        int userid = req.getUserid();
        int[] contentid = req.getContentid();

        Touranddining td = new Touranddining();
        td = touranddiningService.findAllByContentId(contentid[0]);

        String thumbnail = td.getFirstImage();
        int readcount = 0;
        float latitude = td.getMapx();
        float longitude = td.getMapy();
        int areacode = td.getAreacode();

        Route route = new Route();
        route.setUserid(userid);
        route.setAreacode(areacode);
        route.setTitle(title);
        route.setReadcount(readcount);
        route.setThumbnail(thumbnail);
        route.setLatitude(latitude);
        
        route.setLongitude(longitude);
        routeService.writeRoute(route);
        int routeid = routeService.findLastInsertId();

        for (int i = 0; i < contentid.length; i++) {
            int subnum = i;
            Overview ov = new Overview();
            ov = overviewService.findAllByContentId(contentid[i]);
            String suboverview = "";
            if (ov.getOverview() != null) {
                suboverview = ov.getOverview();
            }

            Touranddining td2 = new Touranddining();
            td2 = touranddiningService.findAllByContentId(contentid[i]);
            String subtitle = td2.getTitle();
            String subimg = td2.getFirstImage();
            int subcontentid = contentid[i];
            int subcontenttypeid = td2.getContentTypeId();
            float latitude2 = td2.getMapx();
            float longitude2 = td2.getMapy();
            int areacode2 = td2.getAreacode();

            RouteDetail rd = new RouteDetail();
            rd.setRouteid(routeid);
            rd.setSubnum(subnum);
            rd.setSubtitle(subtitle);
            rd.setSubimg(subimg);
            rd.setSuboverview(suboverview);
            rd.setSubcontentid(subcontentid);
            rd.setSubcontenttype(subcontenttypeid);
            rd.setLatitude(latitude2);
            rd.setLongitude(longitude2);
            rd.setAreacode(areacode2);
            routeService.writeRouteDetail(rd);
        }

        return new ResponseEntity<String>("????????? ??????????????? ?????????????????????.", HttpStatus.OK);
    }

    @GetMapping("/getallroute")
    @ApiOperation(value = "?????? ?????? ????????????") // SWAGGER UI??? ????????? ??????
    public Object getall(@RequestParam int pageno) throws URISyntaxException {
        Pageable request = PageRequest.of(pageno, 12, Sort.by("readcount").descending());
        Page<Route> td = routeService.findAll(request);
        return new ResponseEntity<Page<Route>>(td, HttpStatus.OK);
    }

    @GetMapping("/getroutedetail")
    @ApiOperation(value = "?????? ?????? ?????? ????????????") // SWAGGER UI??? ????????? ??????
    public Object getdetail(@RequestParam int routeid) throws URISyntaxException {
        List<RouteDetail> rd = routeDetailService.findAllByRouteid(routeid);
        return new ResponseEntity<>(rd, HttpStatus.OK);
    }

    @GetMapping("/getmyroute")
    @ApiOperation(value = "?????? ??? ?????? ????????????") // SWAGGER UI??? ????????? ??????
    public Object detail(@RequestParam int userid) throws URISyntaxException {
        List<Route> td = routeService.findAllByUserid(userid);
        return new ResponseEntity<>(td, HttpStatus.OK);
    }
    @GetMapping("/getroutedetailToCart")
    @ApiOperation(value = "????????? ?????? ?????? ?????? ????????? ??????") // SWAGGER UI??? ????????? ??????
    public Object getrouteByAreacode(@RequestParam int userid, @RequestParam int subcontentid) throws URISyntaxException {
        boolean go = touranddiningService.isIn(subcontentid);
        if(go==true){
            Touranddining td = touranddiningService.findAllByContentId(subcontentid);
        
            int contentid = td.getContentId();
            int contenttypeid = td.getContentTypeId();
            float latitude = td.getMapx();
            float longitude = td.getMapy();
            String title = td.getTitle();
            int areacode = td.getAreacode();
    
            Cart toCart = new Cart();
            toCart.setUserid(userid);
            toCart.setContentId(contentid);
            toCart.setContentTypeId(contenttypeid);
            toCart.setTitle(title);
            toCart.setLatitude(latitude);
            toCart.setLongitude(longitude);
            toCart.setAreacode(areacode);
            cartService.writeCart(toCart);
    
            return new ResponseEntity<>("???????????? ????????? ??????????????? ????????? ???????????????.", HttpStatus.OK);
        }
        return new ResponseEntity<>("???????????? ????????? ????????????.", HttpStatus.OK);
    }
    @GetMapping("/getrouteByAreacode")
    @ApiOperation(value = "????????? ?????? ?????? ????????????") // SWAGGER UI??? ????????? ??????
    public Object getroutedetailToCart(@RequestParam int pageno, @RequestParam int areacode) throws URISyntaxException {
        Pageable request = PageRequest.of(pageno, 12, Sort.by("readcount").descending());
        Page<Route> r = routeService.findAllByAreacode(request, areacode);
        return new ResponseEntity<Page<Route>>(r, HttpStatus.OK);
    }


    @GetMapping("/deleteroute")
    @ApiOperation(value = "?????? ????????????") // SWAGGER UI??? ????????? ??????
    public Object deleteroute(@RequestParam int routeid) throws URISyntaxException {
        
        List<RouteDetail> rd = routeDetailService.findAllByRouteid(routeid);
        for (RouteDetail rd2 : rd) {
            int rdid = rd2.getRdid();
            routeDetailService.deleteRouteDetail(rdid);
            System.out.println("????????? ??????");
        }
        routeService.deleteRoute(routeid);
        System.out.println("?????????");
        return new ResponseEntity<>("????????? ??????????????? ?????????????????????.", HttpStatus.OK);
    }

    

}
