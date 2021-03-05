<template>
    <v-container>
        <v-list-item v-for="(item,i) in items" :key="i">
            <v-list-item-content class="pb-0" style="display:block; border: #e991fdb3;">
                <v-divider></v-divider>
                <v-row
                class="mt-3"
                style="font-size:1rem;"
                >
                    <v-col
                        class="text-left ml-2 text-h5"
                        style="font-weight:bold;"
                        ><div id="text1">{{item.nickname}}
                        </div>
                        <v-rating
                        v-model="item.score"
                        background-color="orange lighten-3"
                        color="orange"
                        class="ml-10"
                        small
                        readonly
                        ></v-rating>
                    </v-col>
                    <v-col
                        class="mr-2 text-h5"
                        align="right"
                        ><div id="text1">{{getFormatDate(item.writeTime)}}</div></v-col
                    >
                </v-row>
                <v-row style="font-size:1rem;">
                    <v-col cols="10" class="text-left ml-10 text-h5">
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
    </v-container>
</template>

<script>
import moment from "moment";
import http from "@/util/http-common.js"
import InfiniteLoading from 'vue-infinite-loading';

export default {
    name: "MyReviewComp",
    props: {
        propItem: {type: Array}
    },
    components: {
        InfiniteLoading
    },
    data() {
        return {
            items: this.propItem,
            limit: 1,
        }
    },
    methods: {
        getFormatDate: function(regtime) {
            return moment(new Date(regtime)).format("YYYY년 MM월 DD일");
        },
        infiniteHandler($state) {
            http.get('/review/getByUserid',{
                params: {pageno: this.limit, userid: this.$store.state.userData.id}
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