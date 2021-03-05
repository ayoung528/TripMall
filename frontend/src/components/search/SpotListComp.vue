<template>
    <v-container id="container">
        <v-row class="text-center pa-2 mb-2">
            <v-col class="text-h4">
                <div id="text">검색어에 해당하는 관광지/음식점을 장바구니에 담고 여행 코스를 커스텀해보세요!</div>
            </v-col>
        </v-row>

        <v-form action="/search/list" @submit="checkForm">
            <v-row justify="center">
                <v-col cols="2">
                    <v-combobox
                    v-model="region"
                    :items="regions"
                    item-text="region"
                    item-value="areacode"
                    label="지역"
                    outlined
                    solo
                    color="#9575cd"
                    class="pa-1 text-4"
                    />
                </v-col>
                <v-col cols="2">
                    <v-combobox
                    v-model="type"
                    :items="types"
                    label="구분"
                    outlined
                    solo
                    color="#9575cd"
                    class="pa-1 text-4"
                    />
                </v-col>
                <v-col class="pa-2 mt-2" md="5">
                    <v-text-field
                    placeholder="검색할 관광지 및 음식점을 입력해주세요."
                    outlined
                    v-model="keyword"
                    color="#9575cd"
                    clearable
                    ref="search"
                    name="keyword"
                    solo
                    />
                </v-col>
                <v-col class="pl-0 mt-2" md="1">
                    <v-btn color="#9575cd"
                    dark large @click="goSearch()"
                    class="text-h5"
                    solo
                    >
                    <div id="text">   찾 기</div>
                    </v-btn>
                </v-col>
            </v-row>
        </v-form>

        <v-divider></v-divider>
        <ListComp
        :propItems="items"
        :propAreaCode="region.areacode"
        :propType="type"
        :propKeyword="keyword"
        v-if="items.length !== 0"
        :key="items[0].contentId"
        v-on:cart="cart"
        />
    </v-container>
</template>

<script>
import ListComp from "@/components/search/ListComp.vue";
import http from "@/util/http-common.js";
export default {
    name: 'SpotListComp',
    components: {
        ListComp
    },
    data() {
        return {
            items:[],
            pageno: 0,
            keyword: '',
            type: '관광지',
            types: [
                '관광지',
                '음식점'
            ],
            region:'전체',
            areacode:0,
            regions:[
                {region:'전체', areacode:'0'},
                {region:'서울', areacode:'1'},
                {region:'인천', areacode:'2'},
                {region:'대전', areacode:'3'},
                {region:'대구', areacode:'4'},
                {region:'광주', areacode:'5'},
                {region:'부산', areacode:'6'},
                {region:'울산', areacode:'7'},
                {region:'세종', areacode:'8'},
                {region:'경기', areacode:'31'},
                {region:'강원', areacode:'32'},
                {region:'충북', areacode:'33'},
                {region:'충남', areacode:'34'},
                {region:'경북', areacode:'35'},
                {region:'경남', areacode:'36'},
                {region:'전북', areacode:'37'},
                {region:'전남', areacode:'38'},
                {region:'제주', areacode:'39'},
            ],
            
        }
    },
    methods: {
        goSearch() {
                
            // 1. 모든 음식점
            if((this.region.region==undefined||this.region.region=="전체")&&this.type=="음식점"&&this.keyword==""){
                http.get('/touranddining/food',{
                    params: {pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
            // 2. 지역 음식점
            else if((this.region.region!=undefined||this.region.region!="전체")&&this.type=="음식점"&&this.keyword==""){
                http.get('/touranddining/foodByarea',{
                    params: { areacode: this.region.areacode, pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
            // 3. 키워드 음식점
            else if((this.region.region==undefined||this.region.region=="전체")&&this.type=="음식점"&&this.keyword!=""){
                http.get('/touranddining/foodByname',{
                    params: {name:this.keyword, pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
            // 4. 지역, 키워드 음식점
            else if((this.region.region!=undefined||this.region.region!="전체")&&this.type=="음식점"&&this.keyword!=""){
                http.get('/touranddining/foodBynameAndarea',{
                    params: {areacode: this.region.areacode, name:this.keyword, pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
            // 5. 모든 관광지
            if((this.region.region==undefined||this.region.region=="전체")&&this.type=="관광지"&&this.keyword==""){
                http.get('/touranddining/tour',{
                    params: {pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
            // 6. 지역 관광지
            else if((this.region.region!=undefined||this.region.region!="전체")&&this.type=="관광지"&&this.keyword==""){
                http.get('/touranddining/tourByarea',{
                    params: {areacode: this.region.areacode, pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
            // 7. 키워드 관광지
            else if((this.region.region==undefined||this.region.region=="전체")&&this.type=="관광지"&&this.keyword!=""){
                http.get('/touranddining/tourByname',{
                    params: {name:this.keyword, pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
            // 8. 지역, 키워드 관광지
            else if((this.region.region!=undefined||this.region.region!="전체")&&this.type=="관광지"&&this.keyword!=""){
                http.get('/touranddining/tourBynameAndarea',{
                    params: {areacode: this.region.areacode, name:this.keyword, pageno: this.pageno}
                })
                .then(({data}) => {
                    this.items = data.content;
                })
            }
        },
        checkForm: function(e) {
            if (this.keyword !== "") {
                return true;
            }
            e.preventDefault();
        },
        cart: function() {
            this.$emit('getCart')
        }
    },
}
</script>
<style>
#text, #container{
    font-family: 'CookieRun-Regular';
}
@font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>