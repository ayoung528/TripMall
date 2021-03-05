package com.web.blog.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.web.blog.model.review.Review;
import com.web.blog.model.review.ReviewRequest;
import com.web.blog.model.route.RouteDetail;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;
import com.web.blog.service.ReviewService;
import com.web.blog.service.RouteDetailService;
import com.web.blog.service.TouranddiningService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    RouteDetailService routeDetailService;

    @Autowired
    TouranddiningService touranddiningService;

    @Autowired
    JwtService jwtService;

    @GetMapping("/getByUserid")
    @ApiOperation(value = "유저 번호로 본인 리뷰 목록 받아올 때 사용")
    public Object getByUserid(@RequestParam int pageno, @RequestParam int userid) throws URISyntaxException {
        Pageable request = PageRequest.of(pageno, 12);
        Page<Review> review = reviewService.findAllByUserid(userid, request);
		return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/getByContentid")
    @ApiOperation(value = "contentid로 해당 관광지/음식점에 달린 리뷰 가져오기") // SWAGGER UI에 보이는 이름
    public Object getByContentid(@RequestParam int pageno, @RequestParam int contentid) throws URISyntaxException {
        Pageable request = PageRequest.of(pageno, 5);
        Page<Review> review = reviewService.findAllByContentid(contentid, request);
		return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/alreadyReview")
    @ApiOperation(value = "리뷰 유무 확인") 
    public ResponseEntity<?> alreadyReview(@RequestParam int rdid){
        Optional<RouteDetail> rd = routeDetailService.findByRdid(rdid);
        if(rd.isPresent()){
            if(rd.get().isReview()){
                return new ResponseEntity<>("true", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("false", HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>("no rdid", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "contentid, token 받아서 리뷰 작성") 
	public ResponseEntity<?> writeReply(@RequestHeader String token, @Valid @RequestBody ReviewRequest req, @RequestParam int rdid) {
        int uid = req.getUid();
        String nickname = req.getNickname();
        int contentId = req.getContentId();
        int score = req.getScore();
        String comment = req.getComment();

		User user = jwtService.getUser(token);
		if(user == null || user.getId() != uid) {
			return new ResponseEntity<String>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
        
        Touranddining touranddining = touranddiningService.findAllByContentId(contentId);
		if(touranddining == null){
            return new ResponseEntity<String>("contentid가 유효하지 않습니다.", HttpStatus.NOT_FOUND);
        }

		Review review = new Review();
		review.setNickname(nickname);
		review.setUid(uid);
		review.setContentId(contentId);
		review.setScore(score);
		review.setComment(comment);
		
        reviewService.writeReview(review);
        
        List<Review> reviews = reviewService.findAllByContentid(contentId);
        int sum = 0;
        for(Review r: reviews){
            sum += r.getScore();
        }
        double avg = (sum * 1.0)/reviews.size();

        touranddining.setScore(avg);
        touranddiningService.save(touranddining);

        RouteDetail rd = routeDetailService.findByRdid(rdid).get();
        rd.setReview(true);
        routeDetailService.save(rd);
        
		return new ResponseEntity<String>("리뷰를 성공적으로 작성하였습니다.", HttpStatus.OK);
	}
}