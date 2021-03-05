<template>
    <v-container>
        <v-row class="text-center">
            <v-col class="text-h3">
                 <div id="text1">인기 커스텀 코스</div>
            </v-col>
        </v-row>
        <v-row
        align="center"
        justify="center"
        class="mb-5"
        >
            <v-btn-toggle
                v-model="area_toggle"
                background-color="#fff06491"
                color="#9575cd"
                class="mb-1"
            >
                <v-btn
                v-for="(item, n) in area_first"
                :key="n"
                @click="change1(item.no)"
                large
                class="text-h5"
                >
                    <div id="text1">{{item.title}}</div>
                </v-btn>
            </v-btn-toggle>

            <v-btn-toggle
                v-model="area_toggle2"
                background-color="#fff06491"
                color="#9575cd"
            >
                <v-btn
                v-for="(item, n) in area_second"
                :key="n"
                @click="change2(item.no)"
                large
                class="text-h5"
                >
                    <div id="text1">{{item.title}}</div>
                </v-btn>
            </v-btn-toggle>
        </v-row>

        <v-divider></v-divider>
        <LikedCourseComp
        :propItems="items"
        :propAreaCode="areacode"
        v-on:cart="cart"
        v-if="items.length !== 0"
        :key="items[0].routeid && items.length"
        />
    </v-container>
</template>

<script>
import LikedCourseComp from "@/components/course/LikedCourseComp.vue";
import http from "@/util/http-common.js";

export default {
    name: "LikedCourse",
    components: {
        LikedCourseComp
    },
    data() {
        return {
            keyword: "",
            items: [],
            areacode: 0,
            pageno: 0,
            area_toggle: 0,
            area_toggle2: undefined,
            area_first: [
                {title: '전체', no: 0},
                {title: '서울', no: 1},
                {title: '인천', no: 2},
                {title: '대전', no: 3},
                {title: '대구', no: 4},
                {title: '광주', no: 5},
                {title: '부산', no: 6},
                {title: '울산', no: 7},
                {title: '세종', no: 8},
            ],
            area_second: [
                {title: '경기', no: 31},
                {title: '강원', no: 32},
                {title: '충북', no: 33},
                {title: '충남', no: 34},
                {title: '경북', no: 35},
                {title: '경남', no: 36},
                {title: '전북', no: 37},
                {title: '전남', no: 38},
                {title: '제주', no: 39},
            ],
        }
    },
    methods: {
        change1(data) {
            this.area_toggle2 = undefined;
            this.areacode = data;
            
            if (this.areacode == 0) {
                http
                .get('/route/getallroute', {
                    params: {pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content
                })
            } else {
                http.get('/route/getrouteByAreacode',{
                    params: { areacode: this.areacode, pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
        },
        change2(data) {
            this.area_toggle = undefined;
            this.areacode = data;
            
            http.get('/route/getrouteByAreacode',{
                params: { areacode: this.areacode, pageno: this.pageno}
            })
            .then(({data}) => {
                this.items = data.content;
            })
        },
        cart() {
            this.$emit('cart')
        }
    },
    created() {
        http
        .get('/route/getallroute', {
            params: {pageno: this.pageno}
        })
        .then(({data}) => {
            this.items = data.content
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