package com.web.blog.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.web.blog.dao.UserDao;
import com.web.blog.model.review.Review;
import com.web.blog.model.user.KsignupRequest;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.model.user.UserRequest;
import com.web.blog.model.user.UserResponse;
import com.web.blog.service.JwtService;
import com.web.blog.service.KakaoService;
import com.web.blog.service.KakaoUserInfo;
import com.web.blog.service.ReviewService;

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

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
        @ApiResponse(code = 403, message = "Forbidden", response = UserResponse.class),
        @ApiResponse(code = 404, message = "Not Found", response = UserResponse.class),
        @ApiResponse(code = 500, message = "Failure", response = UserResponse.class) })

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    UserDao userDao;

    @Autowired
    private JwtService jwtService;
    @Autowired
    KakaoService kakao;

    @Autowired
    KakaoUserInfo kakaoUserInfo;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/kakaoLogin")
    @ApiOperation(value = "카카오 로그인") // SWAGGER UI에 보이는 이름
    public Object kakaoLogin(@RequestParam("code") String code)
            throws URISyntaxException {

        String access_Token = "";
        try {
            access_Token = kakao.getAccessToken(code);
            System.out.println(code);
            System.out.println("controller access_token : " + access_Token);
            System.out.println(kakao.getUserInfo(access_Token));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("카카오 로그인 체크 : " + code);

        HashMap<String, Object> userInfo = null;
        userInfo = kakao.getUserInfo(access_Token);

        RedirectView redirectView = new RedirectView();
        String kemail = userInfo.get("email").toString();

        Optional<User> user = userDao.findUserByEmail(kemail);
        System.out.println(user.toString() + "=========================");
        if (user.isPresent()) { // 이미 가입된 사용자
            System.out.println("이미 가입된 사용자입니다.");
            User thisuser = user.get();
            System.out.println(userInfo.toString());
            thisuser.setNickname(userInfo.get("nickname").toString());
            thisuser.setProfileImage(userInfo.get("profile_image_url").toString());
            thisuser.setGender(userInfo.get("gender").toString());
            thisuser.setAgeRange(userInfo.get("age_range").toString());
            
            userDao.save(thisuser);
            User tuser = new User(user.get().getId(), user.get().getEmail(), user.get().getNickname());

            String token = jwtService.createLoginToken(tuser);
            System.out.println(token);
            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("win")){
                redirectView.setUrl("http://localhost:8081/user/login");// 로그인 완료된 페이지
            }else{
                redirectView.setUrl("http://k3b302.p.ssafy.io/user/login");// 로그인 완료된 페이지
            }
            redirectView.addStaticAttribute("token", token);
        } else {// 가입전 사용자
            User newuser = new User();
            System.out.println(userInfo.toString());
            newuser.setEmail(userInfo.get("email").toString());
            newuser.setNickname(userInfo.get("nickname").toString());
            newuser.setProfileImage(userInfo.get("profile_image_url").toString());
            newuser.setGender(userInfo.get("gender").toString());
            newuser.setAgeRange(userInfo.get("age_range").toString());
            
            userDao.save(newuser);
            User thisuser = userDao.getUserByEmail(kemail);
            User tuser = new User(thisuser.getId(), newuser.getEmail(), newuser.getNickname());

            String token = jwtService.createLoginToken(tuser);
            System.out.println("가입하기 성공!");
            String os = System.getProperty("os.name").toLowerCase();
            if(os.contains("win")){
                redirectView.setUrl("http://localhost:8081/user/login");// 로그인 완료된 페이지
            }else{
                redirectView.setUrl("http://k3b302.p.ssafy.io/user/login");// 로그인 완료된 페이지
            }
            redirectView.addStaticAttribute("token", token);

        }
        return redirectView;

    }

    @GetMapping("/getinfo")
    @ApiOperation(value = "유저 정보 받아오기") // SWAGGER UI에 보이는 이름
    public Object getinfo(@RequestHeader String token, @RequestParam int userid) throws URISyntaxException {
        Map<String, Object> result = new HashMap<String, Object>();
        
        User user = jwtService.getUser(token);
		if(user == null || user.getId() != userid) {
			return new ResponseEntity<String>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        User info = kakao.getUserInfo(userid);
        List<Review> reviews = reviewService.findAllByUserid(userid);
        int review_count = reviews.size();
        result.put("user_info", info);
        result.put("review_count", review_count);
		return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
