package com.web.blog.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import com.web.blog.model.cart.Cart;
import com.web.blog.model.cart.CartRequest;
import com.web.blog.model.recommend.Recommend;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.model.user.KsignupRequest;
import com.web.blog.model.user.TokenRequest;
import com.web.blog.model.user.User;
import com.web.blog.service.CartService;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    TouranddiningService touranddiningService;
    @Autowired
    RecommendService recommendService;
    @Autowired
    CartService cartService;
    @Autowired
    JwtService jwtService;

    @GetMapping("/getcart")
    @ApiOperation(value = "카트에 담아 놓은 것들 가져오기") // SWAGGER UI에 보이는 이름
    public Object getcart(@RequestHeader String token, @Valid @RequestParam int userid) throws URISyntaxException {
        User user = jwtService.getUser(token);
		if(user == null || user.getId() != userid) {
			return new ResponseEntity<String>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
		}
        List<Cart> td = cartService.findAllByUserid(userid);
		return new ResponseEntity<>(td, HttpStatus.OK);
    }
    @PostMapping("/intocart")
    @ApiOperation(value = "카트에 담기") // SWAGGER UI에 보이는 이름
    public ResponseEntity<?> intocart( @RequestBody CartRequest req) throws URISyntaxException {
        int userid = req.getUserid();
        int contentId = req.getContentId();
        int contentTypeId = req.getContentTypeId();
        float latitude = req.getLatitude();
        float longitude = req.getLongitude();
        int areacode = req.getAreacode();
        String title = req.getTitle();
		Cart cart = new Cart();
		cart.setUserid(userid);
		cart.setContentId(contentId);
		cart.setContentTypeId(contentTypeId);
        cart.setLatitude(latitude);
        cart.setLongitude(longitude);
        cart.setAreacode(areacode);
        cart.setTitle(title);
		cartService.writeCart(cart);
		
		return new ResponseEntity<String>("카트에 성공적으로 담겼습니다.", HttpStatus.OK);
    }
    @GetMapping("/deletecart")
    @ApiOperation(value = "카트에 담아 놓은 것들 삭제하기") // SWAGGER UI에 보이는 이름
    public Object deletecart(@RequestParam int cid, @RequestHeader String token, @Valid @RequestParam int userid) throws URISyntaxException {
        cartService.deleteCart(cid);
        User user = jwtService.getUser(token);
		if(user == null || user.getId() != userid) {
			return new ResponseEntity<String>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
		}
        List<Cart> td = cartService.findAllByUserid(userid);
		return new ResponseEntity<>(td, HttpStatus.OK);
    }
}