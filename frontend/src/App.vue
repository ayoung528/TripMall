<template>
  <v-app id="main">
    
    <v-row v-if="isLoggedin">
      <v-col
        class="d-flex"
        cols="12"
        lg="3"
        style="margin-left:5%; margin-top:5%;"
      >
        <v-sheet
          class="d-flex rounded-xl"
          elevation="10"
          height="600"
          width="300"
          style="border: 7px solid black;"
        >
        <v-list
        nav
        dense
        class="ma-5"
        width="100%"
        >
        <v-list-item-group
          v-model="group"
          active-class="deep-purple--text text--accent-4"
        >
          <v-list-item disabled>
            <v-avatar
              size="200"
              class="pl-3">
            <v-img v-if="profileImage!='none'"
              class="elevation-5"
              width="50%"
              height="85%"
              :src="profileImage">
            </v-img>
            <v-img v-else
              class="elevation-5"
              width="50%"
              height="85%"
              :src="require('@/assets/noprofile1.png')">
            </v-img>
          </v-avatar>
          </v-list-item>

          <v-list-item style="background-color: #FDD835; padding-left:6%">
            <v-img style="display: inline-block;" width="50" height="50" src="@/assets/basket.png"></v-img>
            <v-list-item-title class="pa-3" style="display: inline-block;"><h1>{{nickname}}님의 장바구니</h1></v-list-item-title>
          </v-list-item>
          <div
            class="scroll-t pt-3"
            style="overflow-x:hidden; overflow-y:auto; width:100%; height:230px; margin:0px; background-color: rgba(255, 255, 128, .5);"
          >
            <v-row justify="center" v-if="!disabledCart">
              <v-flex>
                <v-col
                  v-for="(item, i) in cartList"
                  :key="i"
                >
                  <v-sheet
                    class="my-n3 mx-2"
                    style="background-color: rgba(255, 255, 128, .2)"
                    >
                    <v-row v-if="item.contentTypeId!=39" class="pa-0 mb-2 mx-1" style="background-color: #e991fdb3;">
                      <v-col class="pa-0" cols="9">
                        <v-tooltip right color="purple">
                        <template v-slot:activator="{on, attrs}">
                          <v-btn text v-bind="attrs" v-on="on" class="btn">
                            [{{item.area}}] {{item.title}}
                          </v-btn>
                        </template>
                        <span>{{fullTitle[i]}}</span>
                      </v-tooltip>
                      </v-col>
                      <v-col cols="1">
                        <v-icon
                        id="close-btn"
                        color="red"
                        small
                        @click="removeCart(item.cid)"
                        right
                        >
                          mdi-close
                        </v-icon>
                      </v-col>
                    </v-row>
                    <v-row v-else class="pa-0 mb-2 mx-1" style="background-color: #F5A856;">
                      <v-col class="pa-0" cols="9">
                        <v-tooltip right color="purple">
                        <template v-slot:activator="{on, attrs}">
                          <v-btn text v-bind="attrs" v-on="on" class="btn">
                            [{{item.area}}] {{item.title}}
                          </v-btn>
                        </template>
                        <span>{{fullTitle[i]}}</span>
                      </v-tooltip>
                      </v-col>
                      <v-col cols="1">
                        <v-icon
                        id="close-btn"
                        color="red"
                        small
                        @click="removeCart(item.cid)"
                        right
                        >
                          mdi-close
                        </v-icon>
                      </v-col>
                    </v-row>
                  </v-sheet>
                </v-col>
              </v-flex>
            </v-row>
            <v-row v-else>
              <v-img class="mt-n3" width="80" height="230" :src="require('@/assets/make-route.jpg')"/>
            </v-row>
          </div>
        </v-list-item-group>
          <v-row class="pl-7 mt-3">
            <v-btn
            v-if="isLoggedin"
            @click="goMypage()"
            style="width:40%; margin-right:10%;"
            color="purple"
            dark
            class="btn"
            >
              마이페이지
            </v-btn>
            <v-btn
            v-if="isLoggedin"
            @click="REMOVE_TOKEN"
            style="width:40%;"
            color="purple"
            dark
            class="btn"
            >
              로그아웃
            </v-btn>
          </v-row>
        </v-list>
        </v-sheet>
      </v-col>

      <v-col
        lg="8"
        min-width="200"
      >
        <v-row>

          <v-col>
            <v-sheet
              class="d-flex rounded-xl pl-10"
              elevation="10"
              outlined
              height="60"
              style="border: 7px solid black"
            >
            <v-menu offset-y rounded="b-xl">
              <template v-slot:activator="{attrs, on}">
                <v-btn
                text
                large
                v-bind="attrs" v-on="on"
                class="btn-nav text-h5 font-weight-bold mr-10"
                >
                  <div id="text1">추천 스팟</div>
                </v-btn>
              </template>
              <v-list color="#9575cd">
                <v-list-item @click="goAgeAndGender()" class="btn-nav">
                  <v-list-item-title
                  class="text-h5 font-weight-bold white--text"
                  v-text="tab1"/>
                </v-list-item>
                <v-list-item @click="goFavorite()" class="btn-nav">
                  <v-list-item-title
                  class="text-h5 font-weight-bold white--text"
                  v-text="tab2"/>
                </v-list-item>
              </v-list>
            </v-menu>

            <v-menu offset-y rounded="b-xl">
              <template v-slot:activator="{attrs, on}">
                <v-btn
                text
                large
                v-bind="attrs" v-on="on"
                class="btn-nav text-h5 font-weight-bold mr-10"
                >
                  <div id="text1">추천 코스</div>
                </v-btn>
              </template>
              <v-list color="#9575cd">
                <v-list-item @click="goLikedCourse()" class="btn-nav">
                  <v-list-item-title
                  class="text-h5 font-weight-bold white--text"
                  v-text="tab4"/>
                </v-list-item>
                <v-list-item @click="goCustomedCourse()" class="btn-nav">
                  <v-list-item-title
                  class="text-h5 font-weight-bold white--text"
                  v-text="tab5"/>
                </v-list-item>
              </v-list>
            </v-menu>

            <v-btn
            text
            @click="goSearch()"
            large
            class="btn-nav text-h5 font-weight-bold"
            >
              <div id="text1">검색&코스만들기</div>
            </v-btn>
            
            </v-sheet>
          </v-col>

          <v-col cols="12">
            <v-sheet
              class="d-flex rounded-xl scroll-t"
              elevation="10"
              outlined
              height="620"
              style="overflow-x:hidden; overflow-y:auto; border: 7px solid black"
            >
              <v-main>
                <SearchMain
                v-if="this.search"
                v-on:cart="getCart"
                v-on:empty-cart="emptyCart"
                v-on:toggle="isSearch"
                />
                <Survey
                v-else-if="this.IsSurvey"
                v-on:cart="getCart"
                v-on:reload="reload"
                />
                <FavoriteList
                v-else-if="this.IsFavorite"
                v-on:cart="getCart"
                />
                <CustomedCourse
                v-else-if="this.IsRegion"
                v-on:cart="getCart"
                />
                <LikedCourse
                v-else-if="this.IsCustom"
                v-on:cart="getCart"
                />
                <AgeGenderList
                v-else-if="this.IsAge"
                v-on:cart="getCart"
                />
                <router-view v-else/>
              </v-main>
            </v-sheet>
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <v-main v-else>
      <router-view/>
    </v-main>
    <go-top />
  </v-app>
</template>

<script>
import { mapState, mapMutations} from "vuex";
const BACK_URL = process.env.VUE_APP_BACK_URL;
import axios from "axios";
import GoTop from "@/components/GoTop";
import SearchMain from "@/views/search/Main.vue";
import Survey from "@/views/recommend/Survey.vue";
import FavoriteList from "@/views/recommend/FavoriteList.vue";
import CustomedCourse from "@/views/course/CustomedCourse.vue"
import LikedCourse from "@/views/course/LikedCourse.vue"
import AgeGenderList from "@/views/recommend/AgeGenderList.vue"
import http from "@/util/http-common.js"

export default {
  name: "App",

  components: {
    GoTop,
    SearchMain,
    Survey,
    FavoriteList,
    CustomedCourse,
    LikedCourse,
    AgeGenderList
  },

  data() {
    return {
      tab1: "연령/성별",
      tab2: "취향",
      tab3: "MBTI",
      tab4: "인기 커스텀 코스",
      tab5: "지역별 코스",
      drawer: false,
      group: null,
      id:"",
      nickname:"",
      profileImage:"",
      search: false,
      IsFavorite: false,
      IsSurvey: false,
      IsRegion: false,
      IsCustom: false,
      IsAge: false,
      items: [],
      fullTitle: [],
      errorTemp: undefined,
      disabledCart: false,
    }
  },
  methods: {
    goAgeAndGender() {
      this.search = false;
      this.IsSurvey = false;
      this.IsFavorite = false;
      this.disabledCart = false;
      this.IsRegion = false;
      this.IsCustom = false;
      this.IsAge = true;
    },
    goSearch() {
      this.search = true;
      this.IsSurvey = false;
      this.IsFavorite = false;
      this.IsRegion = false;
      this.IsCustom = false;
      this.IsAge = false;
    },
    goFavorite() {
      this.IsSurvey = true;
      this.search = false;
      this.IsFavorite = false;
      this.disabledCart = false;
      this.IsRegion = false;
      this.IsCustom = false;
      this.IsAge = false;
    },
    goLikedCourse() {
        this.search = false;
        this.IsSurvey = false;
        this.IsFavorite = false;
        this.disabledCart = false;
        this.IsRegion = false;
        this.IsCustom = true;
        this.IsAge = false;
    },
    goCustomedCourse(){
        this.IsRegion = true;
        this.search = false;
        this.IsSurvey = false;
        this.IsFavorite = false;
        this.disabledCart = false;
        this.IsCustom = false;
        this.IsAge = false;
    },
    goMypage() {
      this.$router.push('/user/mypage').catch(() => {});
      this.search = false;
      this.IsSurvey = false;
      this.IsFavorite = false;
      this.disabledCart = false;
      this.IsRegion = false;
      this.IsCustom = false;
      this.IsAge = false;
    },
    reload() {
      this.IsSurvey = false;
      this.IsFavorite = true;
      this.disabledCart = false;
      this.IsRegion = false;
      this.IsCustom = false;
      this.IsAge = false;
    },
    ...mapMutations(["REMOVE_TOKEN"]),
    getCart: function() {
      http
      .get('/cart/getcart', {
        headers: {token: this.$store.state.authToken},
        params: {userid: this.$store.state.userData.id}
      })
      .then(({data}) => {
        this.items = data
        for (var i = 0; i < this.items.length; i++) {
          this.fullTitle[i] = this.items[i].title
          if (this.items[i].title.length > 12) {
            this.items[i].title = this.items[i].title.substring(0,12) + "...";
          }
          if (this.items[i].areacode == 1) {
            this.$set(this.items[i], 'area', '서울')
          }
          else if (this.items[i].areacode == 2) {
            this.$set(this.items[i], 'area', '인천')
          }
          else if (this.items[i].areacode == 3) {
            this.$set(this.items[i], 'area', '대전')
          }
          else if (this.items[i].areacode == 4) {
            this.$set(this.items[i], 'area', '대구')
          }
          else if (this.items[i].areacode == 5) {
            this.$set(this.items[i], 'area', '광주')
          }
          else if (this.items[i].areacode == 6) {
            this.$set(this.items[i], 'area', '부산')
          }
          else if (this.items[i].areacode == 7) {
            this.$set(this.items[i], 'area', '울산')
          }
          else if (this.items[i].areacode == 8) {
            this.$set(this.items[i], 'area', '세종')
          }
          else if (this.items[i].areacode == 31) {
            this.$set(this.items[i], 'area', '경기')
          }
          else if (this.items[i].areacode == 32) {
            this.$set(this.items[i], 'area', '강원')
          }
          else if (this.items[i].areacode == 33) {
            this.$set(this.items[i], 'area', '충북')
          }
          else if (this.items[i].areacode == 34) {
            this.$set(this.items[i], 'area', '충남')
          }
          else if (this.items[i].areacode == 35) {
            this.$set(this.items[i], 'area', '경북')
          }
          else if (this.items[i].areacode == 36) {
            this.$set(this.items[i], 'area', '경남')
          }
          else if (this.items[i].areacode == 37) {
            this.$set(this.items[i], 'area', '전남')
          }
          else if (this.items[i].areacode == 38) {
            this.$set(this.items[i], 'area', '전북')
          }
          else if (this.items[i].areacode == 39) {
            this.$set(this.items[i], 'area', '제주')
          }
        }
      })
    },
    async removeCart(id) {
      await http
      .get('/cart/deletecart', {
        headers: {token: this.$store.state.authToken},
        params: {cid: id, userid: this.$store.state.userData.id}
      })
      .then(({data}) => {
        this.items = data
        for (var i = 0; i < this.items.length; i++) {
          this.fullTitle[i] = this.items[i].title
          if (this.items[i].title.length > 12) {
            this.items[i].title = this.items[i].title.substring(0,12) + "...";
          }
          if (this.items[i].areacode == 1) {
            this.$set(this.items[i], 'area', '서울')
          }
          else if (this.items[i].areacode == 2) {
            this.$set(this.items[i], 'area', '인천')
          }
          else if (this.items[i].areacode == 3) {
            this.$set(this.items[i], 'area', '대전')
          }
          else if (this.items[i].areacode == 4) {
            this.$set(this.items[i], 'area', '대구')
          }
          else if (this.items[i].areacode == 5) {
            this.$set(this.items[i], 'area', '광주')
          }
          else if (this.items[i].areacode == 6) {
            this.$set(this.items[i], 'area', '부산')
          }
          else if (this.items[i].areacode == 7) {
            this.$set(this.items[i], 'area', '울산')
          }
          else if (this.items[i].areacode == 8) {
            this.$set(this.items[i], 'area', '세종')
          }
          else if (this.items[i].areacode == 31) {
            this.$set(this.items[i], 'area', '경기')
          }
          else if (this.items[i].areacode == 32) {
            this.$set(this.items[i], 'area', '강원')
          }
          else if (this.items[i].areacode == 33) {
            this.$set(this.items[i], 'area', '충북')
          }
          else if (this.items[i].areacode == 34) {
            this.$set(this.items[i], 'area', '충남')
          }
          else if (this.items[i].areacode == 35) {
            this.$set(this.items[i], 'area', '경북')
          }
          else if (this.items[i].areacode == 36) {
            this.$set(this.items[i], 'area', '경남')
          }
          else if (this.items[i].areacode == 37) {
            this.$set(this.items[i], 'area', '전남')
          }
          else if (this.items[i].areacode == 38) {
            this.$set(this.items[i], 'area', '전북')
          }
          else if (this.items[i].areacode == 39) {
            this.$set(this.items[i], 'area', '제주')
          }
        }
      })
    },
    emptyCart() {
      this.disabledCart = true;
    },
    isSearch() {
      this.disabledCart = false;
    }
  },
  computed: {
    ...mapState(["isLoggedin", "userData"]),
    cartList() {
      return this.items
    }
  },
  mounted() {
    axios
    .get(`${BACK_URL}/account/getinfo`, {headers: {token : this.$store.state.authToken}, params : {userid:this.$store.state.userData.id}})
    .then(
      ({ data }) => {
        this.id = data.user_info.id
        this.email = data.user_info.email
        this.nickname = data.user_info.nickname
        this.profileImage = data.user_info.profileImage
        this.gender = data.user_info.gender
        this.age_range = data.user_info.ageRange
        this.review_count=data.review_count
        // if(this.profile_image=="none"){
        //   this.profile_image=require('@/assets/noprofile.png')
        // }
        if(this.gender=="none"){
          this.gender="정보가 없습니다."
        }
        if(this.age_range=="none"){
          this.age_range="정보가 없습니다."
        }
      }),

    this.getCart();
    this.IsAge = true
  },
}
</script>

<style>
#main {
  background-color: #E1BEE7;
  background-image: url("./assets/MAL.gif");
  background-size: 100% 100%;
  cursor: url("./cursor.png") 4 30 , auto;
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
#close-btn:hover {
  transform:scale(1.5);
  /* cursor: url("./pointercursor.png") 4 30, pointer; */
}
.btn-nav:hover {
  background-color: #e991fdb3;
  transition: all 0.4s ease-in-out;
  cursor: url("./pointercursor.png") 4 30, pointer;
}
#text1{
    font-family: 'CookieRun-Regular';
}
#text2, #main{
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