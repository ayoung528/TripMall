<template>
    <v-container>
        <v-row class="text-center">
            <v-col class="text-h2 ma-2">
                <div id="text1">설문조사 후 취향에 맞는 스팟을 추천해줍니다!</div>
            </v-col>
        </v-row>
        <v-card
        max-width="1000"
        style="overflow:hidden;"
        >
            <v-card-title class="font-weight-regular">
                <div class="text-h4 black--text" style="text-shadow: 0 0 0;">
                    <div id="text1">{{ currentTitle }}</div>
                </div>
            </v-card-title>
            <div class="text-h5 black--text mb-2 text-left pl-5" v-if="step == 1" style="text-shadow: 0 0 0;">
                <div id="text2">📌 30개의 장소 중 최소 5개, 최대 10개를 선택해주세요.</div>
            </div>
        
            <v-window v-model="step">
                <v-window-item :value="1" class="row justify-content-left p-3">
                <v-card-text v-for="(item, i) in items" :key="i" class="col-12 col-sm-6 col-md-3">
                    <v-img
                    v-if="item.img"
                    class="list-img white--text"
                    :src="item.img"
                    width="200"
                    height="190"
                    />
                    <v-img v-else
                    class="list-img white--text"
                    :src="require('@/assets/noimage.png')"
                    width="130"
                    height="190"
                    >
                        <v-layout column align-center justify-center fill-height>
                            <h1 class="white--text font-weight-bold text-center" style="font-size: 35px;">No Image</h1>
                        </v-layout>
                    </v-img>
                    <v-card-title
                    class="pb-0 mb-1 headline"
                    >
                        <v-row>
                            <v-checkbox
                            v-model="check[i]"
                            color="orange"
                            hide-details
                            class="ma-0 mt-1 ml-5"
                            @click="snackbar = true"
                            />
                            <v-col class="text-left pl-0">
                                <div id="text2">{{i+1}}. {{item.title}}</div>
                            </v-col>
                        </v-row>
                    </v-card-title>
                </v-card-text>
                </v-window-item>
        
                <v-window-item :value="2">
                <div class="text-center">
                    <v-img
                    contain
                    height="400"
                    :src="require('@/assets/survey-result.jpg')"
                    ></v-img>
                </div>
                </v-window-item>
            </v-window>
        
            <v-divider></v-divider>
        
            <v-card-actions>
                <v-btn
                :disabled="step === 1"
                text
                @click="step--"
                >
                <div id="text1">Back</div>
                </v-btn>
                <v-spacer></v-spacer>
                <v-btn
                color="#9575cd"
                @click="goFavoriteList()"
                outlined
                v-if="step === 2"
                class="text-h6"
                >
                <div id="text1">제출하기</div>
                </v-btn>
                <v-btn
                v-else
                :disabled="checkCount < 5 || checkCount > 10"
                color="#9575cd"
                outlined
                class="text-h5"
                @click="next()"
                >
                <div id="text1">다음</div>
                </v-btn>
            </v-card-actions>
        </v-card>

        <v-snackbar
        v-model="snackbar"
        color="#9575cd"
        timeout="-1"
        right
        shaped
        >
        <div id="text2">총 {{checkCount}}개를 선택했습니다. (5개~10개 선택 가능)</div>

            <template v-slot:action="{ attrs }">
                <v-btn
                color="yellow"
                text
                v-bind="attrs"
                @click="snackbar = false"
                >
                Close
                </v-btn>
            </template>
        </v-snackbar>
    </v-container>
</template>

<script>
import { mapState, mapMutations} from "vuex"
import http from "@/util/http-common.js"

export default {
    name: "SurveyComp",
    props: {
        propItems: {type: Array}
    },
    data() {
        return {
            step: 1,
            items: this.propItems,
            check: [],
            id: [],
            snackbar: false
        }
    },
    methods: {
        goFavoriteList() {
            for(var i = 0; i < this.check.length; i++) {
                if(this.check[i] == true) {
                    this.id.push(Number(this.items[i].contentid))
                }
            }
            http
            .post('userbased/submitSurvey',null, {
                params: {
                    token: this.$store.state.authToken,
                    userid: this.$store.state.userData.id,
                    contentid: String(this.id)
                }
            }).then(() =>{
                this.id.splice(0)
                this.$emit('app')
            })
        },
        ...mapMutations(["REMOVE_TOKEN"]),
        next() {
            this.step++
            this.snackbar = false
        }
    },
    computed: {
        currentTitle() {
            switch(this.step) {
                case 1: return '마음에 드는 장소를 선택해주세요 ! (중복 선택 가능)'
                default: return ''
            }
        },
        checkCount() {
            var cnt = 0;
            for (var i = 0; i < this.check.length; i++) {
                if (this.check[i] === true) {
                    cnt++;
                }
            }
            return cnt;
        },
        ...mapState(["isLoggedin", "userData"]),
        cart: state => state.data.cartList,
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