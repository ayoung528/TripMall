<template>
    <v-container>
        <v-sheet
        class="mx-auto"
        max-width="1000"
        >
            <v-row class="text-h4 font-weight-black mx-5">
                <v-col class="text-left">
                    <div id="text1">🗺️ {{this.title}}</div>
                </v-col>
                <v-col class="text-right">
                    <v-btn dark color="error" class="text-h5" @click="remove()"><div id="text1">코스 지우기</div></v-btn>
                </v-col>
            </v-row>
            <v-slide-group
                class="pa-4"
                show-arrows
            >
                <v-slide-item
                v-for="(spot,n) in spots"
                :key="n"
                >
                    <v-card
                        class="ma-4"
                        width="300"
                    >
                        <div v-if="spot.subimg">
                            <v-img :src="spot.subimg"
                            height="200">
                            </v-img>
                            <v-card-text>
                                <div id="text1" style="font-size:15px">{{spot.subtitle}}</div>
                            </v-card-text>
                            <v-card-actions>
                                <v-btn
                                outlined
                                color="#9575cd"
                                @click="writeReview(spot.subcontentid, spot.rdid)"
                                class="text-h6"
                                >
                                <div id="text1">리뷰 작성</div>
                                </v-btn>
                            </v-card-actions>
                        </div>

                        <div v-else>
                            <v-img
                            class="white--text"
                            :src="require('@/assets/noimage.png')"
                            height="300"
                            >
                                <v-layout column align-center justify-center fill-height>
                                    <h1 class="white--text font-weight-bold text-center" style="font-size: 35px;">No Image</h1>
                                </v-layout>
                            </v-img>
                            <v-card-title>
                                <v-row class="fill-height flex-column" justify="space-between">
                                    <div id="text1" style="font-size:15px">{{spot.subtitle}}</div>
                                    <!-- <p class="text-h5 text-left pl-1 font-weight-bold">{{spot.subtitle}}</p> -->
                                </v-row>
                            </v-card-title>
                        </div>
                    </v-card>
                </v-slide-item>
            </v-slide-group>
        </v-sheet>

        <v-dialog color="teal" v-model="dialog" max-width="400">
            <v-card>
                <v-card-title class="text-h4"><div id="text1">정말 코스를 삭제하시겠습니까?</div></v-card-title>

                <v-card-actions>
                <v-spacer></v-spacer>

                <v-btn class="text-h5" text @click="cancel"><div id="text1">아니오</div></v-btn>

                <v-btn class="text-h5 error--text" text @click="removeHandler"><div id="text1">네, 삭제하겠습니다.</div></v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <v-dialog color="teal" v-model="toggle" max-width="600">
            <v-card>
                <v-card-title class="text-h4 purple--text font-weight-bold" style="background-color:#fff06491"><div id="text1">리뷰 글쓰기</div></v-card-title>
                <v-card-text md="11">
                    <v-row class="d-flex justify-end">
                        <v-rating
                        v-model="score"
                        background-color="orange lighten-3"
                        color="orange"
                        class="ml-4"
                        medium
                        />
                    </v-row>
                    <v-row justify="center">
                        <v-col>
                            <v-textarea
                            auto-grow
                            v-model="content"
                            label="리뷰를 작성해주세요. (최대 50자 작성 가능)"
                            outlined
                            required
                            row-height="50"
                            :counter="50"
                            color="purple"
                            :rules="Rules"
                            ref="review"
                            />
                        </v-col>
                    </v-row>
                </v-card-text>

                <v-card-actions style="background-color: #fff06491">
                    <v-btn class="text-h5 font-weight-bold" text @click="cancel"><div id="text1">닫기</div></v-btn>
                    <v-spacer></v-spacer>

                    <v-btn class="text-h5 font-weight-bold error--text" text @click="registReview()"><div id="text1">작성</div></v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>

        <div>
            <v-alert
            elevation="2"
            color="purple"
            border="left"
            v-if="this.alert"
            dismissible
            colored-border
            class="text-left pl-5"
            >
                이미 리뷰를 작성했습니다.
            </v-alert>
        </div>
    </v-container>
</template>

<script>
import http from "@/util/http-common.js"

export default {
    name: "MyRouteComp",
    props: {
        propRoute: {type: Object},
        propTitle: {type: String},
        propId: {type: Number}
    },
    data() {
        return {
            route: this.propRoute,
            spots: [],
            toggle: false,
            title: this.propTitle,
            dialog: false,
            id: this.propId,
            routeid: 0,
            content: '',
            score: 3,
            nickname: '',
            contentId: 0,
            alert: false,
            Rules: [
                v => (v && v.length <= 50) || "리뷰는 50글자 이내만 가능합니다."
            ],
        }
    },
    methods: {
        writeReview(contentid, rdid) {
            http
            .get('/review/alreadyReview', {
                params: {rdid: rdid}
            })
            .then(({data}) => {
                if (data) {
                    this.toggle = false
                    this.alert = true
                    setTimeout(()=>{
                        this.alert=false
                    },3000)
                } else {
                    this.contentId = contentid
                    this.routeid = rdid
                    this.toggle = true
                }
            })
        },
        remove() {
            this.dialog = true
        },
        cancel() {
            this.dialog = false
            this.toggle = false
        },
        removeHandler() {
            http
            .get('/route/deleteroute', {
                params: {routeid: this.id}
            })
            .then()
            this.dialog = false
            this.$emit('reload')
        },
        registReview() {
            if(this.content == "") {
                this.$refs.review.focus()
            } else {
                http
                .post('review', {comment: this.content,
                    contentId: this.contentId,
                    nickname: this.nickname,
                    score: this.score,
                    uid: this.$store.state.userData.id,}, {
                    headers: {'Content-Type': 'application/json',token: this.$store.state.authToken},
                    
                    params: {rdid: this.routeid},
                })
                .then(
                    this.content = "",
                    this.score = 3,
                    this.toggle = false
                )
            }

        }
    },
    created() {
        http
        .get('/route/getroutedetail', {
            params: {routeid: this.route.routeid}
        })
        .then(({data}) => {
            this.spots = data
        })

        http
        .get('/account/getinfo', {headers: {token : this.$store.state.authToken}, params : {userid:this.$store.state.userData.id}})
        .then(
        ({ data }) => {
            this.nickname = data.user_info.nickname
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