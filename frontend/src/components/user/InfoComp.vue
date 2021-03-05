<template>
  <v-container id="text2">
        <v-container class="purple lighten-5" fill-height>
        <v-row
              class="mb-n3 mt-n3 pl-n5 justify-center"
            >
              <v-col
                cols="2"
                class="my-auto mr-3"
                align="center"
              >
                <v-img id="icon" src="../../assets/email.png"/><br />
                <span  style="font-size:1rem;">이메일</span><br />
                <span id="text1" style="font-size:1.5rem;">{{ email }}</span>
              </v-col>
              <v-col
                cols="2"
                class="my-auto mx-3"
                align="center"
              >
                <v-img id="icon" src="../../assets/gender.png"/><br />
                <span style="font-size:1rem;">성별</span><br />
                <span  id="text1" style="font-size:1.5rem;">{{ gender }}</span>
              </v-col>
              <v-col
                cols="2"
                class="my-auto mx-3"
                align="center"
              >
                <v-img id="icon" src="../../assets/age.png"/><br />
                <span style="font-size:1rem;">연령</span><br />
                <span  id="text1" style="font-size:1.5rem;">{{ age_range }}</span>
              </v-col>
              <v-col cols="2" class="my-auto mx-3 mypage-nav" align="center" @click="goMyRoute()">
                <v-img id="icon" src="../../assets/route.png" style="position: relative;"/>

                <v-img class="mt-n16" src="../../assets/Ex.png" style="position: absolute; width: 7%;height: 7%; margin-left:8%;"/>
                <dfn data-info="클릭 시, 나만의 루트를 삭제 또는 해당 스팟의 리뷰를 쓰는 화면으로 전환합니다:)" style="margin-left:75%; padding-top:50%;">
                </dfn>

                <div style="font-size:1rem;">나의 루트</div>
                <div  id="text1" style="font-size:1.5rem;">총 {{ route_count }}회</div>
              </v-col>
              <v-col cols="2" class="my-auto ml-3 mypage-nav" align="center">
                <v-img id="icon" src="../../assets/review.png" @click="goMyReview()"/><br />
                <span style="font-size:1rem;">나의 리뷰</span><br />
                <span  id="text1" style="font-size:1.5rem;">총 {{ review_count }}회</span>
              </v-col>
            </v-row>
        </v-container>
        <div v-show="!this.IsMyRoute && !this.IsMyReview">
          <div style="width:80%; display: inline-block">
            <v-sheet elevation="3" color="white" class="ml-3 mr-9 mt-7" style="width:100%">
              <v-avatar
                class="elevation-4 mb-1 mr-2"
                color="purple lighten-1"
              >
                <v-icon dark>mdi-map</v-icon>
              </v-avatar>
              My map
              <v-sheet>
                <div id="map" style="height:550px;" />
              </v-sheet>
            </v-sheet>
          </div>
          <div class="mt-8 scroll-t" id="timeline" style="display: inline-block; height:650px;">
            <div class="timeline">
            <ul>
              <div id="result"></div>
            </ul>
            </div>
          </div>
        </div>
        <MyRoute
        :propRoute="routeList"
        v-on:back="back()"
        v-if="this.IsMyRoute"
        />
        <MyReview
        v-else-if="this.IsMyReview"
        v-on:back="back()"
        />
        
  </v-container>
</template>

<script src="https://unpkg.com/vue@2.5.16/dist/vue.js"></script>
<script tpye="text/javascript">
import axios from "axios";
const BACK_URL= process.env.VUE_APP_BACK_URL;
import MyRoute from "@/views/user/MyRoute.vue"
import MyReview from "@/views/user/MyReview.vue"
// const APP_KEY = process.env.VUE_APP_KAKAO_APP_KEY;

export default {
    name:'InfoComp',
    props: {
      propItem: {type: Array},
      propCount: {type: Number},
      propRoute: {type: Array}
    },
    components: {
      MyRoute,
      MyReview
    },
    data() {
        return{
            id:"",
            email:"",
            nickname:"",
            gender:"",
            age_range:"",
            userData:[],
            authToken:"",
            route_count:this.propCount,
            review_count:"",
            routeList:this.propRoute,
            kakaomap :'',
            markers:[],
            routeDetailList:this.propItem,
            clickList:[],
            IsMyRoute: false,
            IsMyReview: false,
        }
    },
    watch:{
      pick(){
        console.log("update")
      }
    },
    
    mounted() { 
      
      window.kakao && window.kakao.maps ? this.initMap() : this.addScript(); 
              
        axios
        .get(`${BACK_URL}/account/getinfo`, {headers: {token : this.$store.state.authToken}, params : {userid:this.$store.state.userData.id}})
        .then(
          ({ data }) => {
            this.userData = data.user_info
            this.id = this.userData.id
            this.email = this.userData.email
            this.nickname = this.userData.nickname
            this.gender = this.userData.gender
            this.age_range = this.userData.ageRange
            this.review_count=data.review_count
            if(this.gender=="none"){
              this.gender="정보가 없습니다."
            }else{
              if(this.userData.gender == "female") {
                this.gender = "여성"
              } else { this.gender = "남성" }
            }
            if(this.age_range=="none"){
              this.age_range="정보가 없습니다."
            }
          })
    },
    methods : {
      goMyReview() {
        this.IsMyRoute = false
        this.IsMyReview = true
      },
      goMyRoute() {
        if (this.routeList.length != 0)
          this.IsMyRoute = true
          this.IsMyReview = false
      },
      back() {
        this.IsMyRoute = false
        this.IsMyReview = false
      },
        initMap() { 
            var container = document.getElementById("map");
            var selected_list2=this.routeList;
            var reallist=this.routeDetailList;
            var sumlat = 0;
            var sumlng = 0;
            var minlat = 0;
            var minlng = 0;
            var maxlat = 0;
            var maxlng = 0;
            var level = 0;
            var latlng = 0;

            if(selected_list2.length==0){
                sumlat =36.450701;
                sumlng =127.570667;
                level = 17
                latlng = new kakao.maps.LatLng(sumlat,sumlng);
            }
            else{
                minlat = selected_list2[0].longitude
                maxlat = selected_list2[0].longitude
                minlng = selected_list2[0].latitude
                maxlng = selected_list2[0].latitude
                for (var i in selected_list2){
                    sumlat += selected_list2[i].longitude
                    sumlng += selected_list2[i].latitude
                    minlat = Math.min(minlat,selected_list2[i].longitude)
                    maxlat = Math.max(maxlat,selected_list2[i].longitude)
                    minlng = Math.min(minlng,selected_list2[i].latitude)
                    maxlng = Math.max(maxlng,selected_list2[i].latitude)
                }
                var maxdiff = Math.round(Math.max((maxlat - minlat)*3,(maxlng - minlng)*1.5 ))
                level = 10 + maxdiff
                latlng = new kakao.maps.LatLng(sumlat/(selected_list2.length), sumlng/(selected_list2.length));
            }
            var options = { center: latlng, 
            level: level 
            }; 

            //마커추가하려면 객체를 아래와 같이 하나 만든다. 
            if(this.kakaomap ==''){
                this.kakaomap = new kakao.maps.Map(container, options);
            } else{
                this.kakaomap.setCenter(latlng)
                this.kakaomap.setLevel(level)
                for(var j =0; j< this.markers.length;j++){
                    this.markers[j].setMap(null);
                }
                this.markers = []
            }
            for (var k in selected_list2){
                var imageSize = new kakao.maps.Size(60, 60);  
                var imageSrc = '';
                imageSrc=selected_list2[k].thumbnail
                // console.log(imageSrc)
                if(imageSrc.length==0){
                  imageSrc=require("../../assets/noimage.png")
                }
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
            
                var marker = new kakao.maps.Marker({
                    map:  this.kakaomap, // 마커를 표시할 지도
                    position: new kakao.maps.LatLng(selected_list2[k].longitude, selected_list2[k].latitude), // 마커를 표시할 위치
                    text : selected_list2[k].title, 
                    image : markerImage // 마커 이미지 
                });
                
                // marker.setImage(MarkerImage);
                // marker.setMap(this.kakaomap);
                this.markers.push(marker);
                var infowindow = new kakao.maps.InfoWindow({
                content: `<div fill align="center">`+selected_list2[k].title+`</div>`// 인포윈도우에 표시할 내용
                });
                
                // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
                kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(this.kakaomap, marker, infowindow));
                kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
                kakao.maps.event.addListener(marker,'click',makeClickListener(selected_list2[k].routeid));
            }
            function makeOverListener(map, marker, infowindow) {
                return function() {
                    infowindow.open(map, marker);
                };
            }

            // 인포윈도우를 닫는 클로저를 만드는 함수입니다 
            function makeOutListener(infowindow) {
                return function() {
                    infowindow.close();
                };
            }
            function makeClickListener(routeid) {
                return function() {
                  var massage='';
                  for(var i in reallist){
                      if(routeid===reallist[i].routeid){
                        console.log(reallist[i].subimg)
                        if(reallist[i].subimg.length==0){
                          reallist[i].subimg='https://1.bp.blogspot.com/-D2I7Z7-HLGU/Xlyf7OYUi8I/AAAAAAABXq4/jZ0035aDGiE5dP3WiYhlSqhhMgGy8p7zACNcBGAsYHQ/s400/no_image_square.jpg'
                        }

                        massage+=('<li>'+
                        '<div class="date">'+
                        '<h2>'+
                        reallist[i].subtitle+
                        // '<br><button class="review">📝 리뷰</button>'+
                        '<img src="'+reallist[i].subimg+'" width="100" height="80">'+
                        '</h2>'+
                        '</div>'+
                        '<div class="point"></div>'+
                        '</li>');
                    }
                  }
                  // massage+='<br><div><button id="del" class="delete" id="cousedelete">🗑️ 코스 삭제</a></button></div>'
                  var resultDiv = document.getElementById('result'); 
                  resultDiv.innerHTML=massage;
                
                };
                  
            }
          
            
        },
        addScript() { 
            const script = document.createElement("script"); 
            /* global kakao */ 
            script.onload = () => kakao.maps.load(this.initMap); 
            script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=5a3a01519efdeba53b7a039bffafd62d`; 
            document.head.appendChild(script); 
            },
        
        
    },
}
</script>

<style>
.mypage-nav:hover{
  cursor: pointer;
}
#timeline {
  float: right;
  width: 20%;
}
.scroll-t::-webkit-scrollbar {
  width: 7px;
}
.scroll-t::-webkit-scrollbar-track {
  background-color: transparent;
}
.scroll-t::-webkit-scrollbar-thumb {
  border-radius: 4px;
  background-color: gray;
}
.scroll-t::-webkit-scrollbar-button {
  width: 0;
  height: 0;
}
#icon {
  width: 40%;
  height: 40%;
}
.timeline {
    position: relative;
    align-items: right;
}
.timeline ul li {
    margin-bottom: 50px;
    list-style-type: none;
    display: flex;
    flex-direction: row;
    align-items: center;
}

.point {
    min-width: 20px;
    height: 20px;
    background-color: #be9fe1;
    border-radius: 100%;
    z-index: 2;
    border: 3px #333333 solid;
    position: relative;
}

.timeline ul li .date {
    width: 85%;
    padding: 0 20px;
    font-weight: normal;
}

.timeline ul li .date h2 {
    background-color: #e1ccec;
    width: 120px;
    text-align: center;
    padding: 5px 10px;
    border-radius: 10px;
}
.timeline ul li .date .review {
    background-color: #c778f1;
    margin-top: 10px;
    width: 100px;
    text-align: center;
    padding: 5px 10px;
    border-radius: 10px;
}
.review:hover {
  color: white;
}
.delete {
    background-color: #fcf595;
    margin-left: 30px;
    width: 140px;
    height: 60px;
    text-align: center;
    padding: 7px 10px;
    border-radius: 10px;
    font-family: 'TmoneyRoundWindExtraBold';
    font-size: 200%;
}

.timeline ul li {
    flex-direction: row-reverse;
}

.timeline::before {
    content: "";
    position: absolute;
    height: 100%;
    width: 3px;
    left: 20%;
    background-color: #333333;
}
@font-face {
    font-family: 'TmoneyRoundWindExtraBold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/TmoneyRoundWindExtraBold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
#text1{
    font-family: 'CookieRun-Regular';
}
#text2{
    font-family: 'JSDongkang-Regular';
}
@font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
@font-face {
    font-family: 'JSDongkang-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/JSDongkang-RegularA1.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
dfn {
  /* background: rgba(0,0,0,0.2);
  border-bottom: dashed 1px rgba(0,0,0,0.8); */
  padding: 0 1.5em;
  cursor: help;
  font-style: 'JSDongkang-Regular';
  position: relative;
}
dfn::after {
  content: attr(data-info);
  display: inline;
  position: absolute;
  top: 22px; left: 0;
  opacity: 0;
  width: 210px;
  font-size: 13px;
  font-weight: 700;
  line-height: 1.5em;
  padding: 0.5em 0.8em;
  background: rgba(0,0,0,0.8);
  color: #fff;
  pointer-events: none; /* This prevents the box from apearing when hovered. */
  transition: opacity 250ms, top 250ms;
}
dfn::before {
  content: '';
  display: block;
  position: absolute;
  top: 12px; left: 20px;
  opacity: 0;
  width: 0; height: 0;
  border: solid transparent 5px;
  border-bottom-color: rgba(0,0,0,0.8);
  transition: opacity 250ms, top 250ms;
}
dfn:hover {z-index: 2;} /* Keeps the info boxes on top of other elements */
dfn:hover::after,
dfn:hover::before {opacity: 1;}
dfn:hover::after {top: 30px;}
dfn:hover::before {top: 20px;}
</style>