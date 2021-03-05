package com.web.blog.controller.collaborated;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import com.web.blog.model.collaborated.Survey;
import com.web.blog.model.collaborated.Surveyresult;
import com.web.blog.model.collaborated.Userbased;
import com.web.blog.model.review.Review;
import com.web.blog.model.touranddining.Touranddining;
import com.web.blog.model.user.User;
import com.web.blog.service.JwtService;
import com.web.blog.service.ReviewService;
import com.web.blog.service.TouranddiningService;
import com.web.blog.service.collaborated.SurveyresultService;
import com.web.blog.service.collaborated.UserbasedService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api/userbased")
public class UserbasedController {

    @Autowired
    UserbasedService userbasedService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    TouranddiningService touranddiningService;
    @Autowired
    SurveyresultService surveyresultService;
    @Autowired
    JwtService jwtService;

    @GetMapping("/getList")
    @ApiOperation(value = "유저 취향 반영 추천") // SWAGGER UI에 보이는 이름
    public Object getcart(@RequestHeader String token) throws URISyntaxException {
        Comparator<Review> comparator = new Comparator<Review>(){
            @Override
            public int compare(Review a, Review b){
                return b.getScore() - a.getScore();
            }
        };
        User user = jwtService.getUser(token);
        List<Review> myreviews = reviewService.findAllByUserid(user.getId());
        LinkedHashSet<Integer> myreviewContentid = new LinkedHashSet<>();
        for(Review r : myreviews){
            myreviewContentid.add(r.getContentId());
        }
        
        List<Review> myTopReviews = new ArrayList<>();
        for(Review review : myreviews){
            if(review.getScore() >= 4){
                myTopReviews.add(review);
            }
        }
        Collections.sort(myTopReviews, comparator);
        LinkedHashSet<Integer> recommendContentid = new LinkedHashSet<>();
        for(Review review : myTopReviews){
            if(recommendContentid.size()>30) break;
            List<Userbased> recommends = userbasedService.findAllBySelectedContent(review.getContentId());
            for(Userbased ub : recommends){
                if(ub.getSimilarity()>=0.6 && !myreviewContentid.contains(ub.getRecommendedContent())){
                    recommendContentid.add(ub.getRecommendedContent());
                }
            }
        }

        List<Surveyresult> mySurvey = surveyresultService.findAllByUid(user.getId());
        if(mySurvey.size() == 0){
            return new ResponseEntity<>("잘못된 접근입니다.", HttpStatus.NOT_FOUND);
        }else{
            for(Surveyresult sr : mySurvey){
                if(!recommendContentid.contains(sr.getContentId())){
                    recommendContentid.add(sr.getContentId());
                }
            }
        }

        List<Touranddining> recommends = new ArrayList<>();
        for(int cid : recommendContentid){
            recommends.add(touranddiningService.findAllByContentId(cid));
        }

		return new ResponseEntity<>(recommends, HttpStatus.OK);
    }

    
    @GetMapping("/getSurveyList")
    @ApiOperation(value = "설문조사 리스트 얻기") // SWAGGER UI에 보이는 이름
    public Object getSurveyList(@RequestParam int userid) throws URISyntaxException {
        List<Surveyresult> mySurvey = surveyresultService.findAllByUid(userid);
        System.out.println(mySurvey);
        if(mySurvey.size() != 0){
            return new ResponseEntity<>("true", HttpStatus.OK);
        }
        List<Survey> surveyList = new ArrayList<>();
        LinkedHashSet<Integer> randomid = new LinkedHashSet<>();
        for(int i = 0; i < 60; i++)
            randomid.add(i);

        Random random = new Random();

        while(randomid.size() > 30){
            int r = random.nextInt(randomid.size());
            if(randomid.remove(r)){
                Survey s = new Survey(contentids[r], titles[r], imgs[r]);
                surveyList.add(s);
            }
        }
        
		return new ResponseEntity<>(surveyList, HttpStatus.OK);
    }

    @PostMapping("/submitSurvey")
    @ApiOperation(value = "설문조사 제출")
    public Object submitSurvey(@RequestParam String token, @RequestParam int userid, @RequestParam String contentid) throws URISyntaxException {
        System.out.println("설문조사 제출 중");
        User user = jwtService.getUser(token);
		if(user == null || user.getId() != userid) {
			return new ResponseEntity<String>("토큰이 유효하지 않습니다.", HttpStatus.UNAUTHORIZED);
        }
        System.out.println(contentid);
        String[] splitid = contentid.split(",");
        for(int i = 0; i < splitid.length; i++){
            int cid = Integer.parseInt(splitid[i]);
            Surveyresult sr = new Surveyresult();
            sr.setUid(userid);
            sr.setScore(5);
            sr.setContentId(cid);
            surveyresultService.save(sr);
        }

        return new ResponseEntity<>("설문이 정상적으로 제출되었습니다.", HttpStatus.OK);
    }

    static int[] contentids = {125503, 128767, 127801, 125850, 129000, 126470, 1074432, 127325,
        128526, 126848, 250432, 126537, 322836, 1921423, 2022638, 126581, 
        128578, 604009, 250094, 129263, 127869, 560481, 761181, 128208,
        769761, 127916, 126535, 729167, 1295827, 130511, 130284, 129703,
        507599, 386053, 663045, 141799, 2028440, 2496910, 1957556, 593570,
        2044096, 131489, 131519, 2033064, 131044, 131332, 131335, 131251,
        131641, 131917, 557269, 131925, 1362516, 1905660, 782779, 231994,
        2010199, 404081, 135059, 133769};
    static String[] titles = {"제부도", "을왕리해수욕장", "석모도", "안면도",
        "추암 촛대바위", "외돌개(제주)", "무등산 주상절리대", "광주 충효동 왕버들 군",
        "경주 동궁과 월지", "해동 용궁사(부산)", "진영 봉하마을과 노무현 대통령 생가", "북촌한옥마을",
        "휴애리자연생활공원", "김해 롯데워터파크", "하늘물빛정원", "외도 보타니아",
        "섬진강기차마을", "쁘띠프랑스", "담양 메타세쿼이아길", "대관령 양떼목장",
        "대한다원", "파주 출판도시", "SK텔레콤 New ICT체험관 T.um", "외고산 옹기마을",
        "다대포 꿈의 낙조분수", "청남대", "남산서울타워", "반포대교 달빛무지개분수",
        "예술의전당 한가람미술관", "간송미술관(서울 보화각)", "코엑스 아쿠아리움", "국립중앙박물관",
        "[명예대표축제]얼음나라 화천산천어축제 2020", "[문화관광축제] 평창송어축제 2020", "오색별빛정원전 2020", "고양국제꽃박람회 2021",
        "서울 밤도깨비 야시장 2020", "1890 남산골야시장 2019", "창경궁 야간 상시관람 2019", "페인터즈: 히어로",
        "로하스 가족공원 워터캠핑장", "함허동천야영장", "그랜드하얏트 서울 아이스링크", "광주-기아 챔피언스 필드",
        "예당저수지(예당관광지)", "문래청소년수련관 수영장(실내)", "김포스포츠센터(실내수영장)", "태평양수영장(실내)",
        "문경활공랜드", "한국패러글라이딩학교", "에어로마스터", "조나단 패러글라이딩 스쿨",
        "서울마리나 요트&클럽", "남이섬 짚와이어", "짚라인 문경", "카트조이",
        "죽림원", "덕인갈비", "석파랑", "어가일식"};
    static String[] imgs = {"http://tong.visitkorea.or.kr/cms/resource/34/2482734_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/24/1784824_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/73/2661873_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/75/2426575_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/04/2653704_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/91/2023691_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/47/1558947_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/20/2369720_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/61/2612561_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/21/2364321_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/75/1611275_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/06/2512006_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/47/2615547_image2_1.bmp",
        "http://tong.visitkorea.or.kr/cms/resource/10/1921410_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/96/2548096_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/93/2612893_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/89/2032689_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/07/2562707_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/03/2612603_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/45/2365245_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/33/2652133_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/64/1591764_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/00/2512100_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/63/2601663_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/64/2498864_image2_1.JPG",
        "http://tong.visitkorea.or.kr/cms/resource/43/2601643_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/95/2660695_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/81/2616881_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/23/1807823_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/18/2384418_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/86/2433886_image2_1.JPG",
        "http://tong.visitkorea.or.kr/cms/resource/75/2658575_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/22/2362022_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/53/2362453_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/95/2582795_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/80/2601280_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/53/2376253_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/60/2546960_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/99/2005799_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/01/2038001_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/25/2044125_image2_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/96/1578996_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/49/1868049_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/36/2015036_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/46/795046_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/45/1898345_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/35/1861835_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/40/1974440_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/53/1613453_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/55/1875455_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/35/1862635_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/12/1847512_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/80/2601180_image2_1.bmp",
        "http://tong.visitkorea.or.kr/cms/resource/33/2433833_image2_1.JPG",
        "http://tong.visitkorea.or.kr/cms/resource/39/1959339_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/03/1846503_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/51/1870451_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/30/401330_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/47/1973247_image3_1.jpg",
        "http://tong.visitkorea.or.kr/cms/resource/39/1361239_image3_1.jpg"};
}