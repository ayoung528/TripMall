<template>
    <v-container id="text2">
        <v-row class="text-center">
            <v-col class="text-h4">
                <div id="text1"> 나만의 여행코스를 만들어보세요!</div>
            </v-col>
        </v-row>
        <v-row id="custom">
            <v-col cols="4">
                <h1 id="text1">장바구니</h1>
                <v-sheet
                    elevation="3"
                    color="white"
                    width="200"
                    height="200"
                    class="scroll-t" style="overflow-x:hidden; overflow-y:auto; margin:0 auto;"
                >
                <draggable class="list-group" :list="list1" group="people" @change="move()">
                    <div
                    class="list-group-item"
                    v-for="(element, index) in list1"
                    :key="index"
                    >
                    <v-row v-if="element.contentTypeId!=39" class="pa-2 mb-1 mt-1 mx-1" style="background-color: #e991fdb3;">
                    <div id="text2">[{{element.area}}] {{ element.title }}</div>
                    </v-row>
                    <v-row v-else class="pa-2 mb-1 mt-1 mx-1" style="background-color: #F5A856;">
                    <div id="text2">[{{element.area}}] {{ element.title }}</div>
                    </v-row>
                    </div>
                </draggable>
                </v-sheet>
            </v-col>
            <v-col cols="4">
                <div style="width:10%"></div>
                <h1 id="text1">나의 코스</h1>
                <v-sheet
                    elevation="3"
                    color="white"
                    width="200"
                    height="200"
                    class="scroll-t" style="overflow-x:hidden; overflow-y:auto; margin:0 auto;"
                >
                <div id ="box" class="scroll-t" style="overflow-x:hidden; overflow-y:auto;">
                <draggable class="list-group" :list="list2" group="people" @change="move()">
                    <div
                    class="list-group-item"
                    v-for="(element, index) in list2"
                    :key="index"
                    >
                    <v-row v-if="element.contentTypeId!=39" class="pa-2 mb-1 mt-1 mx-1" style="background-color: #e991fdb3;">
                     <div id="text2">[{{element.area}}] {{ element.title }}</div>
                    </v-row>
                    <v-row v-else class="pa-2 mb-1 mt-1 mx-1" style="background-color: #F5A856;">
                     <div id="text2">[{{element.area}}] {{ element.title }}</div>
                    </v-row>
                    </div>
                </draggable>
                </div>
                </v-sheet>
            </v-col>
            <v-col cols="1">
                <v-btn @click="LoadCustom()" style="width:40%; margin-right:10%; margin-bottom:20%; background-color:#CE93D8;">
                불러오기
                </v-btn>
                <v-btn  @click="SaveCustom()" style="width:40%; margin-top:20%; background-color:#CE93D8;">
                코스만들기
                </v-btn>
            </v-col>
        </v-row>
        <v-row id="custom">
        <v-sheet elevation="3" color="white" class="ml-3 mr-9 mt-7" style="width:80%;">
            <v-avatar
            class="elevation-4 mb-1 mr-2"
            color="purple lighten-1"
            >
            <v-icon dark>mdi-map</v-icon>
            </v-avatar>
            Map
            <v-sheet>
            <div id="map" style="height:400px;" />
            </v-sheet>
        </v-sheet>
        </v-row>
    </v-container>
</template>

<script>
import draggable from "vuedraggable";
import Swal from 'sweetalert2';
import axios from "axios";
import { mapActions } from "vuex";
const BACK_URL = process.env.VUE_APP_BACK_URL;
export default {
    name: 'MakeCourseComp',
    components: {
        draggable,
    },
    props: {
        propItems: {type: Array},
        propArea: {type: Array}
    },
    data() {
        return {
            list1: this.propItems,
            list2: [],
            area: this.propArea,
            contentid:[],
            kakaomap :'',
            markers:[],
            tourSpotIcon:['https://blog.kakaocdn.net/dn/SPQa4/btqKhkuCVb1/04h5vSI3DpW0uu07u7B941/img.png',
    'https://blog.kakaocdn.net/dn/pe3Gt/btqKibc7VPl/JMG0zmTTAZuBAegMSA2c9k/img.png',
    'https://blog.kakaocdn.net/dn/cohULe/btqKjdhw6FU/6b0kW79Yif93wNFlG70Am0/img.png',
    'https://blog.kakaocdn.net/dn/UGYLa/btqKibEadVP/UpOEGxKFkNDkZwjwKWxPQ1/img.png',
    'https://blog.kakaocdn.net/dn/bvf92J/btqKqyqRQ04/Fy5PFkKUKKdiHh51Is4NGK/img.png',
    'https://blog.kakaocdn.net/dn/bdmShR/btqKhkuCVbs/ojP7mziDENGz9Y6D5nD7ZK/img.png',
    'https://blog.kakaocdn.net/dn/bHpHqL/btqKhlf0cPd/9S1Is1yv0OdWbqZT0PwmEk/img.png',
    'https://blog.kakaocdn.net/dn/Hp8PE/btqKgE7VicQ/P6F4MkEdrvhsrmb6eKwlok/img.png',
    'https://blog.kakaocdn.net/dn/bUJbmS/btqKmoJiK44/ZvWMnrkwxUas06or7b7jkk/img.png',
    ],

    foodSpotIcon:['https://blog.kakaocdn.net/dn/s7zKy/btqKibqFGbn/KApKCa9pF76nuHWBU8ryQk/img.png',
    'https://blog.kakaocdn.net/dn/bkdFGn/btqKgFMxUtI/xPDP0p2kJT82l9sjULFMlk/img.png',
    'https://blog.kakaocdn.net/dn/HgH33/btqKhjJdgg2/A344bGg8RKWioQpX9QK7L1/img.png',
    'https://blog.kakaocdn.net/dn/BJMKZ/btqKkNQoqEk/E2kJ42ayb0D6f0Ryg1hNRk/img.png',
    'https://blog.kakaocdn.net/dn/bKLXIr/btqKgFyZfVE/kRSKk5hF2ToZBTHP2pFP61/img.png',
    'https://blog.kakaocdn.net/dn/bOv5TL/btqKlT3Qoj7/lPR9Vx061fTmy4vTAKY7OK/img.png',
    'https://blog.kakaocdn.net/dn/eHw9vf/btqKhj3AdbD/wjouln3abRXTomBgG2w2b1/img.png',
    'https://blog.kakaocdn.net/dn/bjYlVH/btqKlUhpx3U/zVBvKO21koI0vuKxDwjNI1/img.png',
    'https://blog.kakaocdn.net/dn/b1OFLG/btqKlUIti88/3pnbUkhaSyNawkxJdUEAKK/img.png',
    ],
        }
    },
    mounted(){
        window.kakao && window.kakao.maps ? this.initMap() : this.addScript(); 
    },
    methods: {
        ...mapActions("data", ["setList2"]),
        async setList(){
            await this.setList2(this.list2)
        },
        move() {
            // console.log(this.list1);
            // console.log(this.list2);
            this.initMap()
        },
        SaveCustom(){
            
            if(this.list2.length > 0){
                Swal.fire({
                title: '여행경로 이름을 입력해주세요!',
                icon: 'info',
                showCancelButton: true,
                showLoaderOnConfirm: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: '저장하기',
                cancelButtonText: '취소',
                input: 'text',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                preConfirm: (title) => {
                    console.log(this.list2.contentId)
                    for(var i=0;i<this.list2.length;i++){
                        this.contentid[i]=this.list2[i].contentId
                    }
                    axios
                    .post(`${BACK_URL}/route/createroute`,{
                        contentid:this.contentid,
                        title:title,
                        userid: this.$store.state.userData.id
                    })
                    .then(()=>{
                        Swal.fire({
                        title:'저장완료!',
                        width:'300px',
                        icon:'success',
                        showConfirmButton: false,
                        timer: 1000,
                      })
                      this.list2=[]
                      this.setList()
                      this.initMap()
                    })
                },
                allowOutsideClick: () => !Swal.isLoading()
                })
                
            }
        },

        async LoadCustom(){
            var titleList=[];
            var Options={};
            axios.get(`${BACK_URL}/route/getmyroute`,{params:{userid: this.$store.state.userData.id}})
            .then((data)=>{
                var res=data.data
                for(var n in res){
                    var obj={id:res[n].routeid, data : res[n].title};
                    titleList.push(obj)
                }
                for(var k=0;k<titleList.length;k++){
                    Options[titleList[k].id]=titleList[k].data
                }
                const { value: course } = Swal.fire({
                title: '불러올 경로를 선택해주세요!',
                input: 'select',
                inputOptions: Options,
                inputPlaceholder: '선택',
                showCancelButton: true,
                inputValidator: (value) => {
                    return new Promise((resolve) => {
                    if (value === '') {
                        resolve('이름을 입력해주세요:)')
                    } else {
                        axios.get(`${BACK_URL}/route/getroutedetail`, {params : {routeid:value}})
                        .then((data) => {
                            var items = []
                            // console.log(data)
                            for(var k=0; k < data.data.length; k++){
                                var item = []
                                if (data.data[k].areacode == 1) {
                                    item.area='서울'
                                }
                                else if (data.data[k].areacode == 2) {
                                    item.area='인천'
                                }
                                else if (data.data[k].areacode == 3) {
                                    item.area='대전'
                                }
                                else if (data.data[k].areacode == 4) {
                                    item.area='대구'
                                }
                                else if (data.data[k].areacode == 5) {
                                    item.area='광주'
                                }
                                else if (data.data[k].areacode == 6) {
                                    item.area='부산'
                                }
                                else if (data.data[k].areacode == 7) {
                                    item.area='울산'
                                }
                                else if (data.data[k].areacode == 8) {
                                    item.area='세종'
                                }
                                else if (data.data[k].areacode == 31) {
                                    item.area='경기'
                                }
                                else if (data.data[k].areacode == 32) {
                                    item.area='강원'
                                }
                                else if (data.data[k].areacode == 33) {
                                    item.area='충북'
                                }
                                else if (data.data[k].areacode == 34) {
                                    item.area='충남'
                                }
                                else if (data.data[k].areacode == 35) {
                                    item.area='경북'
                                }
                                else if (data.data[k].areacode == 36) {
                                    item.area='경남'
                                }
                                else if (data.data[k].areacode == 37) {
                                    item.area='전남'
                                }
                                else if (data.data[k].areacode == 38) {
                                    item.area='전북'
                                }
                                else if (data.data[k].areacode == 39) {
                                    item.area='제주'
                                }
                                item.contentId = data.data[k].subcontentid
                                item.contentTypeId = data.data[k].subcontenttype
                                item.latitude = data.data[k].latitude
                                item.longitude = data.data[k].longitude
                                item.title = data.data[k].subtitle
                                items.push(item)
                            }
                        // console.log(items)
                            this.list2 = items
                            this.setCart()
                            this.initMap()

                        }).catch(()=>{
                            
                            this.initMap()
                        })
                        resolve()
                    }
                    })
                }
                })
                if (course) {
                    Swal.fire(`You selected: ${course}`)
                }
            })
        },

        initMap() { 
            var container = document.getElementById("map");
            var selected_list2=this.list2;
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
                var imageSize = new kakao.maps.Size(40, 40);  
                var imageSrc = '';
                if(selected_list2[k].contentTypeId!=39){
                    imageSrc = this.tourSpotIcon[k]
                }else{
                    imageSrc = this.foodSpotIcon[k]
                }
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
            
                var marker = new kakao.maps.Marker({
                    map:  this.kakaomap, // 마커를 표시할 지도longitude
                    position: new kakao.maps.LatLng(selected_list2[k].longitude, selected_list2[k].latitude), // 마커를 표시할 위치
                    // position: new kakao.maps.LatLng(selected_list2[k].latitude, selected_list2[k].longitude), // 마커를 표시할 위치
                    text : selected_list2[k].title, 
                    image : markerImage // 마커 이미지 
                });
                
                this.markers.push(marker);
                var infowindow = new kakao.maps.InfoWindow({
                content: `<div fill align="center">`+selected_list2[k].title+`</div>`// 인포윈도우에 표시할 내용
                });

                // for문에서 클로저를 만들어 주지 않으면 마지막 마커에만 이벤트가 등록됩니다
                kakao.maps.event.addListener(marker, 'mouseover', makeOverListener(this.kakaomap, marker, infowindow));
                kakao.maps.event.addListener(marker, 'mouseout', makeOutListener(infowindow));
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
        }, 
        addScript() { 
            const script = document.createElement("script"); 
            /* global kakao */ 
            script.onload = () => kakao.maps.load(this.initMap); 
            script.src = `http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=5a3a01519efdeba53b7a039bffafd62d`; 
            document.head.appendChild(script); 
            
        }
    },
    created() {
        this.$emit('empty-cart')
    }
}
</script>
<style>
#custom {
  display: flex;
  justify-content: center;
  align-items: center;
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
</style>