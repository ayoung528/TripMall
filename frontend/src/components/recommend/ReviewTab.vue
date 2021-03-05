<template>
    <div class="mx-13">
        <div v-if="this.items.length" class="text-h5 mb-3"><div id="text2">총 {{this.items.length}} 개의 리뷰가 있습니다.</div></div>
        <div v-else class="text-h3 mb-3 text-center"><div id="text2">아직 등록된 리뷰가 없습니다.</div>
            <div class="mt-3"><div id="text2">첫 리뷰의 주인공이 되어보세요! ✍ ...</div></div>
        </div>
        <v-list-item v-for="(item,i) in items" :key="i">
            <v-list-item-content class="pb-0" style="display:block; border: #e991fdb3;">
            <v-divider></v-divider>
                <v-row
                class="d-flex justify-content-between mt-3"
                style="font-size:1rem;"
                >
                    <v-col
                        class="py-0 ml-2 text-h5"
                        style="font-weight:bold;"
                        ><div id="text1">{{item.nickname}}</div></v-col
                    ><v-col
                        class="py-0 mr-2 text-h5"
                        align="right"
                        ><div id="text1">{{getFormatDate(item.writeTime)}}</div></v-col
                    >
                </v-row>
                <v-row class="d-flex justify-content-between">
                    <v-rating
                    v-model="item.score"
                    background-color="orange lighten-3"
                    color="orange"
                    class="ml-4"
                    small
                    readonly
                    ></v-rating>
                </v-row>
                <v-row style="font-size:1rem;">
                    <v-col cols="10" class="my-auto ml-2 text-h5">
                        <div
                        ><div id="text2">{{item.comment}}</div></div>
                    </v-col>
                </v-row>
            </v-list-item-content>
        </v-list-item>

        <infinite-loading @infinite="infiniteHandler" spinner="spiral">
            <div slot="no-more">
                 <div id="text2">💬 더 이상 데이터가 없습니다 :)</div>
            </div>
        </infinite-loading>
    </div>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common.js"
import InfiniteLoading from 'vue-infinite-loading';

export default {
    name: "ReviewTab",
    props: {
        propReviewList: {type: Array},
        propId: {type: Number}
    },
    components: {
        InfiniteLoading
    },
    data: function() {
        return {
            limit: 1,
            totalPage: 7,
            items: this.propReviewList,
            id: this.propId
        }
    },
    methods: {
        getFormatDate: function(regtime) {
        return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
        },
        infiniteHandler($state) {
            http.get('/review/getByContentid',{
                params: {contentid: this.id, pageno: this.limit}
            })
            .then(({data}) => {
                setTimeout(() => {
                    if(data.content.length != 0) {
                        this.items = this.items.concat(data.content);
                        $state.loaded();
                        this.limit += 1;

                        if (data.content.length / 5 < 1) {
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