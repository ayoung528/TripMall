<template>
    <v-container class="row justify-content-left p-3">
        <CustomedCourseDetail
        :propId="contentid"
        v-on:goBack="goBack"
        v-on:cart="setCart"
        v-if="this.isDetail"
        />
        <div
        class="col-12 col-sm-6 col-md-3"
        v-for="(item, index) in items"
        :key="index"
        v-else
        >
            <v-card max-width="400" style="overflow:hidden;">
                <div style="height:200px;width:100%;">
                    <v-img
                    v-if="item.firstImage"
                    class="list-img white--text"
                    :src="item.firstImage"
                    @click="getDetail(item.contentId)"
                    />
                    <v-img v-else
                    class="list-img white--text"
                    :src="require('@/assets/noimage.png')"
                    @click="getDetail(item.contentId)"
                    >
                        <v-layout column align-center justify-center fill-height>
                            <h1 class="white--text font-weight-bold text-center" style="font-size: 35px;">No Image</h1>
                        </v-layout>
                    </v-img>
                </div>
                <div style="height: 80px; width:100%;">
                    <v-card-title
                    class="pb-0 mb-1 headline"
                    @click="getDetail(item.contentId)"
                    >
                        <v-row>
                            <v-col class="text-center">
                                <div id="text1">{{item.title}}</div>
                            </v-col>
                        </v-row>
                    </v-card-title>
                </div>
                <br>
                <v-divider></v-divider>

                <v-card-actions>
                    <v-row>
                        <v-col class="text-right">
                            <div id="text2">views {{item.readcount}}</div>
                        </v-col>
                    </v-row>
                </v-card-actions>
            </v-card>
        </div>

        <infinite-loading v-if="!this.isDetail" @infinite="infiniteHandler" spinner="spiral">
            <div slot="no-more">
                 <div id="text2">💬 더 이상 데이터가 없습니다 :)</div>
            </div>
        </infinite-loading>
        
    </v-container>
</template>

<script>
import http from "@/util/http-common.js";
import CustomedCourseDetail from "@/views/course/CustomedCourseDetail.vue"
import InfiniteLoading from 'vue-infinite-loading';

export default {
    name: "CustomedCourseComp",
    props: {
        propItems: {type: Array},
        propAreaCode: {type: Number}
    },
    components: {
        InfiniteLoading,
        CustomedCourseDetail
    },
    data(){
        return {
            items: this.propItems,
            limit: 1,
            areacode: this.propAreaCode,
            isDetail: false,
            contentid: 0
        }
    },
    methods: {
        setCart() {
            this.$emit('cart')
        },
        goBack() {
            document.getElementsByClassName('container')[0].scrollIntoView({behavior: "smooth"})
            this.isDetail = false
        },
        getDetail: function(contentId) {
            this.isDetail = true
            this.contentid = contentId
        },
        toggle: function() {
            this.dialog = false;
        },
        infiniteHandler($state) {
            http.get('/recommend/getrecommendByAreacode',{
                params: { areacode: this.areacode, pageno: this.limit}
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