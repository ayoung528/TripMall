<template>
    <v-container class="row justify-content-left p-3">
        <div
        class="col-12 col-sm-6 col-md-3"
        v-for="(item, index) in items"
        :key="index"
        >
            <v-card max-width="400" style="overflow:hidden;">
                <div style="height:200px;width:100%;">
                    <v-img
                    v-if="item.firstImage"
                    class="list-img white--text"
                    :src="item.firstImage"
                    @click="getDetail(item.firstImage, item.title, item.contentId)"
                    />
                    <v-img v-else
                    class="list-img white--text"
                    :src="require('@/assets/noimage.png')"
                    @click="getDetail(item.firstImage, item.title, item.contentId)"
                    >
                        <v-layout column align-center justify-center fill-height>
                            <h1 class="white--text font-weight-bold text-center" style="font-size: 35px;">No Image</h1>
                        </v-layout>
                    </v-img>
                </div>
                <div style="height: 80px; width:100%;">
                    <v-card-title
                    class="pb-0 mb-1 headline"
                    @click="getDetail(item.firstImage, item.title, item.contentId)"
                    >
                        <v-row>
                            <v-col id="text1" class="text-center">
                                {{item.title}}
                            </v-col>
                        </v-row>
                    </v-card-title>
                </div>
                <v-card-text class="text--primary">
                    <div id="text2" class="mb-3">
                        {{item.addr1}}
                    </div>
                    <div class="d-flex justify-content-between mt-2">
                        <div class="d-flex" style="font-size:large;">
                        <v-rating
                        v-model="item.score"
                        readonly
                        background-color="orange lighten-3"
                        color="orange"
                        medium
                        />
                        </div>
                    </div>
                    <v-row class="text-right">
                        <v-col>
                            <v-btn color="#9575cd" outlined class="text-h5" @click="goCart(item)">
                                <div id="text1" >담기</div>
                            </v-btn>
                        </v-col>
                    </v-row>
                </v-card-text>
            </v-card>
        </div>
        <RecommendDetailComp
        :propImg="this.img"
        :propTitle="this.title"
        :propOverview="this.overview"
        :propType="this.detailtype"
        :propId="this.id"
        :propItems="this.detail"
        v-if="this.dialog"
        v-on:exit="toggle"
        />
        <infinite-loading @infinite="infiniteHandler" spinner="spiral">
            <div slot="no-more">
                 💬 더 이상 데이터가 없습니다 :)
            </div>
        </infinite-loading>
    </v-container>
</template>

<script>
import http from "@/util/http-common.js";
import InfiniteLoading from 'vue-infinite-loading';
import RecommendDetailComp from "@/components/course/recommendDetailComp.vue";
import {mapState} from "vuex";

export default {
    name: "CustomedCourseComp",
    props: {
        propItems: {type: Array},
        propAreaCode: {type: String},
        propType: {type:String},
        propKeyword: {type:String},
    },
    components: {
        InfiniteLoading,
        RecommendDetailComp
    },
    data(){
        return {
            items: this.propItems,
            limit: 1,
            areacode: this.propAreaCode,
            type:this.propType,
            keyword:this.propKeyword,
            dialog: false,
            contentTitle: '',
            contentImg: '',
            rating:this.propItems.score,
            url:'',
            img: '',
            title: '',
            overview: '',
            id: '',
            detailtype: 0,
            detail: []
        }
    },
    methods: {
        async getDetail(subdetailimg, subname, subid) {
            await http
            .get('/info/getInfo', {
                params: {contentid: subid}
            })
            .then(({data}) => {
                this.detail = data
                if (Object.keys(data).includes('overview')) {
                this.id = data.overview.contentId
                this.overview = data.overview.overview;
                if(data.overview.contentTypeId == 39) this.detailtype = data.overview.contentTypeId
                } else {
                this.detailtype = 0
                }
            })

            this.img = subdetailimg;
            this.title = subname;
            this.dialog = true;
        },
        toggle: function() {
            this.dialog = false;
        },
        infiniteHandler($state) {
            // 1. 모든 음식점
            if((this.areacode==undefined||this.areacode=="0")&&this.type=="음식점"&&this.keyword==""){
                this.url='/touranddining/food'
            }
            // 2. 지역 음식점
            else if((this.areacode!=undefined||this.areacode!="0")&&this.type=="음식점"&&this.keyword==""){
                this.url='/touranddining/foodByarea'
            }
            // 3. 키워드 음식점
            else if((this.areacode==undefined||this.areacode=="0")&&this.type=="음식점"&&this.keyword!=""){
                this.url='/touranddining/foodByname'
            }
            // 4. 지역, 키워드 음식점
            else if((this.areacode!=undefined||this.areacode!="0")&&this.type=="음식점"&&this.keyword!=""){
                this.url='/touranddining/foodBynameAndarea'
            }
            // 5. 모든 관광지
            if((this.areacode==undefined||this.areacode=="0")&&this.type=="관광지"&&this.keyword==""){
                this.url='/touranddining/tour'
            }
            // 6. 지역 관광지
            else if((this.areacode!=undefined||this.areacode!="0")&&this.type=="관광지"&&this.keyword==""){
                this.url='/touranddining/tourByarea'
            }
            // 7. 키워드 관광지
            else if((this.areacode==undefined||this.areacode=="0")&&this.type=="관광지"&&this.keyword!=""){
                this.url='/touranddining/tourByname'
            }
            // 8. 지역, 키워드 관광지
            else if((this.areacode!=undefined||this.areacode!="0")&&this.type=="관광지"&&this.keyword!=""){
                this.url='/touranddining/tourBynameAndarea'
            }

            http.get(this.url,{
                params: { areacode: this.areacode, type:this.type, name:this.keyword, pageno: this.limit}
            })
            .then((data) => {
                setTimeout(() => {
                    if(data.data.content.length != 0) {
                        this.items = this.items.concat(data.data.content);
                        $state.loaded();
                        this.limit += 1;

                        if (data.data.content.length / 12 < 1) {
                            $state.complete();
                        }
                    } else {
                        $state.complete();
                    }
                }, 1000)
            })
        },
        goCart(item) {
            http
            .post('/cart/intocart', {
                areacode: item.areacode,
                contentId: item.contentId,
                contentTypeId: item.contentTypeId,
                latitude: item.mapx,
                longitude: item.mapy,
                title: item.title,
                userid: this.$store.state.userData.id
            })
            .then(() => {
                this.$emit('cart')
            })
        },
    },
    computed: {
    ...mapState(["isLoggedin", "userData"])
  },
}
</script>

<style scoped>
.list-img {
  height: 200px !important;
  width: 100% !important;
  /* object-fit: cover; */
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}

.list-img:hover {
  transform: scale(1.1);
  transition: all 0.3s ease-in-out;
  cursor: pointer;
}

.list-title {
  cursor: pointer;
  height: 70px;
  align-items: baseline;
  text-overflow: ellipsis;
  overflow: hidden;
  line-height: 1.8rem;
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