<template>
    <v-container>
        <v-dialog
        v-model="dialog"
        width="800"
        min-width="700"
        persistent
        >

        <v-card>
            <v-card-title class="text-h4 font-weight-bold white--text"
            style="background-color: #9575cd">
            <div id="text1">"{{this.title}}" 상세보기</div>
            </v-card-title>

            <v-tabs
            v-model="tab"
            centered
            color="#c83cc8ff"
            >
                <v-tabs-slider></v-tabs-slider>

                <v-tab href="#tab-1" style="font-size: 13px;">
                    <div id="text1">상세 정보</div>
                </v-tab>
                <v-tab href="#tab-2" style="font-size: 13px;">
                    <div id="text1">관련 추천</div>
                </v-tab>
                <v-tab href="#tab-3" style="font-size: 13px;">
                    <div id="text1">리뷰 목록</div>
                </v-tab>
            </v-tabs>

            <v-tabs-items v-model="tab">
                <v-tab-item value="tab-1" class="pa-5">
                    <DetailTab12
                    :propImg="this.img"
                    :propOverview="this.items"
                    v-if="this.detailType == 12"
                    />
                    <DetailTab14
                    :propImg="this.img"
                    :propItems="this.items"
                    v-else-if="this.detailType == 14"
                    />
                    <DetailTab15
                    :propImg="this.img"
                    :propItems="this.items"
                    v-else-if="this.detailType == 15"
                    />
                    <DetailTab39
                    :propImg="this.img"
                    :propItems="this.items"
                    v-else-if="this.detailType == 39"
                    />
                    <DetailTab28
                    :propImg="this.img"
                    :propItems="this.items"
                    v-else-if="this.detailType == 28"
                    />
                    <DetailTab
                    :propImg="this.img"
                    :propOverview="this.overview"
                    v-else
                    />
                    <v-divider></v-divider>

                    <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="#c83cc8ff"
                        outlined
                        @click="exit()"
                    >
                        <div id="text1">cancel</div>
                    </v-btn>
                    </v-card-actions>
                </v-tab-item>

                <v-tab-item value="tab-2" class="pa-5">
                    <RecommendRelationComp
                    :propItem="menuList"
                    :propOverview="overviewList"
                    :propDistance="distanceList"
                    />
                    <v-divider></v-divider>

                    <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="#c83cc8ff"
                        outlined
                        @click="exit()"
                    >
                        <div id="text1">cancel</div>
                    </v-btn>
                    </v-card-actions>
                </v-tab-item>

                <v-tab-item value="tab-3" class="pa-5">
                    <ReviewTab
                    :propReviewList="this.reviewList"
                    :propId="this.id"
                    />
                    <v-divider></v-divider>

                    <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="#c83cc8ff"
                        outlined
                        @click="exit()"
                    >
                        <div id="text1">cancel</div>
                    </v-btn>
                    </v-card-actions>
                </v-tab-item>
            </v-tabs-items>

        </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
import DetailTab12 from "@/components/recommend/DetailTab12.vue"
import DetailTab15 from "@/components/recommend/DetailTab15.vue"
import DetailTab39 from "@/components/recommend/DetailTab39.vue"
import DetailTab28 from "@/components/recommend/DetailTab28.vue"
import DetailTab from "@/components/recommend/DetailTab.vue"
import DetailTab14 from "@/components/recommend/DetailTab14.vue"
import ReviewTab from "@/components/recommend/ReviewTab.vue"
import RecommendRelationComp from "@/components/course/recommendRelationComp.vue"
import http from "@/util/http-common.js"

export default {
    name: "recommendDetailComp",
    components: {
        DetailTab12,
        DetailTab14,
        DetailTab15,
        DetailTab39,
        DetailTab28,
        DetailTab,
        ReviewTab,
        RecommendRelationComp
    },
    props: {
        propImg: {type: String},
        propTitle: {type: String},
        propOverview: {type: String},
        propType: {type: Number},
        propId: {type: Number},
        propItems: {type: Object}
    },
    data(){
        return {
            dialog: true,
            tab: null,
            rating: 3,
            item: {},
            img: this.propImg,
            title: this.propTitle,
            overview: this.propOverview,
            type: this.propType,
            id: this.propId,
            menuList: [],
            distanceList: [],
            overviewList: [],
            reviewList: [],
            items: this.propItems,
            detailType: 0
        }
    },
    methods: {
        exit: function() {
            this.$emit('exit');
        }
    },
    mounted() {
        if (this.items && this.items.overview && this.items.info != "N") {
            if (this.items.overview.contentTypeId == 12) {
                this.detailType = 12
            } else if (this.items.overview.contentTypeId == 14) {
                this.detailType = 14
            } else if (this.items.overview.contentTypeId == 15) {
                this.detailType = 15
            } else if (this.items.overview.contentTypeId == 39) {
                this.detailType = 39
            } else if (this.items.overview.contentTypeId == 28) {
                this.detailType = 28
            }
        }

        if(this.type == 39) {
            http
            .get('/menubased/getMenubasedList', {
                params: {contentid: this.id}
            })
            .then(({data}) => {
                this.menuList = data
            })
        }
        
        http
        .get('overviewbased/getOverviewbasedList', {
            params: {contentid: this.id}
        })
        .then(({data}) => {
            this.overviewList = data
        })

        http
        .get('/distancebased/getDistancebasedList', {
            params: {contentid: this.id}
        })
        .then(({data}) => {
            this.distanceList = data
        })

        http
        .get('/review/getByContentid',{
            params: {contentid: this.id, pageno: 0}
        })
        .then(({data}) => {
            this.reviewList = data.content
        })
    }
}
</script>
<style>
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