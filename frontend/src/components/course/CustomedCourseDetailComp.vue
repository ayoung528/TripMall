<template>
  <v-container fluid class="pa-0">
    <v-carousel :show-arrows="false" cycle interval="3000">
      <v-carousel-item
        v-for="(item,i) in items"
        :key="i"
        :src="item.subdetailimg">
        <v-row class="fill-height" align="center" justify="center">
          <v-col class="text-h1 font-weight-bold"
          style="text-align:center; width: 80%; text-shadow: -1px 0 purple, 0 1px purple, 1px 0 purple, 0 -1px purple;">
            <div id="text1">{{i+1}}. {{item.subname}}</div>
          </v-col>
        </v-row>
      </v-carousel-item>
    </v-carousel>

    <v-row class="ma-5" style="background-color: #fff06491">
      <v-col>
        <v-row>
          <v-col class="text-h3 text-left font-weight-bold">
            <div id="text1">💁 코스 설명</div>
          </v-col>
        </v-row>
        <v-row class="ma-5">
          <v-col
          class="text-h4 text-left pa-2"
          v-html="courseOverview"
          style="word-spacing: 3px; line-height: 150%; font-family: 'JSDongkang-Regular';"
          />
        </v-row>
      </v-col>
    </v-row>

    <v-timeline
      align-top
      dense
      class="mb-15"
    >
      <v-timeline-item
        v-for="(item, i) in items"
        :key="i"
        color="#9575cd"
      >
        <v-card
          color="#e991fdb3"
          max-width="800"
        >
          <v-card-title class="purple--text font-weight-bold" style="font-size: 18px;">
            <div id="text1">{{item.subname}}</div>
          </v-card-title>
          <v-card-text class="white text--primary">
            <v-row>
              <v-col>
                <v-img
                v-if="item.subdetailimg"
                class="list-img white--text"
                :src="item.subdetailimg"
                @click="spotDetail(item.subnum, item.subdetailimg, item.subname, item.subcontentid)"
                />
                <v-img
                v-else
                class="list-img white--text"
                :src="require('@/assets/noimage.png')"
                @click="spotDetail(item.subnum, item.subdetailimg, item.subname, item.subcontentid)"
                >
                  <v-layout column align-center justify-center fill-height>
                    <h1 class="white--text font-weight-bold text-center" style="font-size: 35px;">No Image</h1>
                  </v-layout>
                </v-img>
              </v-col>
            </v-row>
            <v-card-text class="text-h4" v-if="subInfo[i] != '제공되는 번호가 없습니다 :('" v-html="subInfo[i]"/>
            <v-card-text class="text-h4" v-else>
              <div id="text1">🙅 {{subInfo[i]}}</div>
            </v-card-text>

            <v-card-text class="text-h5" v-if="subOverview[i] != '제공되는 사이트 주소가 없습니다 :('">
              🌐 <a :href="subOverview[i]" target="_blank"><div id="text1">{{subOverview[i]}}</div></a>
            </v-card-text>
            <v-card-text class="text-h5" v-else>
              <div id="text1">🙅 {{subOverview[i]}}</div>
            </v-card-text>
            <v-card-subtitle class="text-h5"><div id="text2">{{item.subdetailoverview}}</div></v-card-subtitle>
            
          </v-card-text>
          <v-card-actions style="background-color: white;">
            <v-col class="text-right">
              <v-btn outlined color="purple" style="font-size: 13px;" @click="setCart(item.subcontentid)">
                <div id="text1">장바구니에 담기</div>
              </v-btn>
            </v-col>
          </v-card-actions>
        </v-card>
      </v-timeline-item>
    </v-timeline>

    <RecommendDetailComp
    :propImg="this.img"
    :propTitle="this.title"
    :propItems="this.detail"
    :propOverview="this.overview"
    :propType="this.type"
    :propId="this.id"
    v-if="this.dialog"
    v-on:exit="toggle"
    />
  </v-container>
</template>

<script>
import RecommendDetailComp from "@/components/course/recommendDetailComp.vue";
import http from "@/util/http-common.js";

export default {
  name: 'CustomedCourseDetailComp',
  props: {
    propItems: {type: Array},
    propOverview: {type: String}
  },
  components: {
    RecommendDetailComp
  },
  data() {
    return {
      items: this.propItems,
      dialog: false,
      img: undefined,
      title: undefined,
      overviews: [],
      overview: undefined,
      subInfo: [],
      subOverview: [],
      courseOverview: this.propOverview,
      type: 0,
      id: 0,
      detail: undefined
    }
  },
  methods: {
    async spotDetail(subnum, subdetailimg, subname, subid) {
      await http
      .get('/info/getInfo', {
        params: {contentid: subid}
      })
      .then(({data}) => {
        this.detail = data
        if (Object.keys(data).includes('overview')) {
          this.id = data.overview.contentId
          if(data.overview.contentTypeId == 39) this.type = data.overview.contentTypeId
        } else {
          this.type = 0
        }
      })

      this.img = subdetailimg;
      this.title = subname;
      this.overview = this.overviews[subnum];
      this.dialog = true;
    },
    toggle: function() {
      this.dialog = false;
    },
    setCart(id) {
      http
      .get('/route/getroutedetailToCart', {
        params: {subcontentid: id, userid: this.$store.state.userData.id}
      })
      .then(() => {
          this.$emit('cart')
      })
      .catch(err => {
        console.log('데이터 없음 ',err)
      })
    }
  },
  async created() {
    for(var i = 0; i < this.items.length; i++) {
      this.overviews[i] = this.items[i].subdetailoverview;

      await http.get('info/getInfo', {
        params: {contentid: this.items[i].subcontentid}
      })
      .then(({data}) => {
        if (data.overview && data.info) {
          this.subInfo.push('📞 ' + data.info.infoCenter)
          this.subOverview.push(data.overview.homepage)

          if (this.subInfo[i] == '📞 N' || this.subInfo[i] == '📞 undefined' || this.subInfo[i] == "📞 ") {
            this.subInfo.splice(i, 1, '제공되는 번호가 없습니다 :(')
          }
          if (this.subOverview[i] === undefined || this.subOverview[i] == "") {
            this.subOverview.splice(i, 1, '제공되는 사이트 주소가 없습니다 :(')
          }
        } else {
          this.subInfo.push('제공되는 번호가 없습니다 :(')
          this.subOverview.push('제공되는 사이트 주소가 없습니다 :(')
        }
      })

      if (this.items[i].subdetailoverview.length > 80) {
        this.items[i].subdetailoverview = this.items[i].subdetailoverview.substring(0,80) + "...";
      }
    }
  },
}
</script>

<style scoped>
.list-img {
  height: 380px !important;
  width: 100% !important;
  /* object-fit: cover; */
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}

.list-img:hover {
  transform: scale(0.9);
  transition: all 0.3s ease-in-out;
  cursor: pointer;
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