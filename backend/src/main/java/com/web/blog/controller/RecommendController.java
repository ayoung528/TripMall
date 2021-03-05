package com.web.blog.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.web.blog.model.recommend.Recommend;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.model.user.KsignupRequest;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.model.user.UserRequest;
import com.web.blog.model.user.UserResponse;
import com.web.blog.service.JwtService;
import com.web.blog.service.KakaoService;
import com.web.blog.service.KakaoUserInfo;
import com.web.blog.service.RecommendService;
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
@RequestMapping("/api/recommend")
public class RecommendController {

    @Autowired
    TouranddiningService touranddiningService;
    @Autowired
    RecommendService recommendService;

    @GetMapping("/getrecommend")
    @ApiOperation(value = "모든 추천 루트 정보 가져오기") // SWAGGER UI에 보이는 이름
    public Object getrecommend(@RequestParam int pageno) throws URISyntaxException {
        Pageable request = PageRequest.of(pageno, 12, Sort.by("readcount").descending());
		Page<Touranddining> td = touranddiningService.findAllByContentTypeId(request, 25);
		return new ResponseEntity<Page<Touranddining>>(td, HttpStatus.OK);
    }

    @GetMapping("/getrecommendByAreacode")
    @ApiOperation(value = "지역별 추천 루트 정보 가져오기") // SWAGGER UI에 보이는 이름
    public Object getrecommendByAreacode(@RequestParam int pageno, @RequestParam int areacode) throws URISyntaxException {
        Pageable request = PageRequest.of(pageno, 12, Sort.by("readcount").descending());
		Page<Touranddining> td = touranddiningService.findAllByContentTypeIdAndAreacode(request, 25, areacode);
		return new ResponseEntity<Page<Touranddining>>(td, HttpStatus.OK);
    }

    @GetMapping("/getdetail")
    @ApiOperation(value = "추천 루트 상세 정보 가져오기") // SWAGGER UI에 보이는 이름
    public Object detail(@RequestParam int contentid) throws URISyntaxException {
        List<Recommend> td = recommendService.findAllByContentid(contentid);
		return new ResponseEntity<>(td, HttpStatus.OK);
    }

}
