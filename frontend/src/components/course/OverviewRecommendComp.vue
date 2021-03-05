<template>
    <v-card width="300" class="mr-2" elevation="2">
        <div v-if="image">
            <v-img :src="image"
            height="300">
            </v-img>
            <v-card-title>
                <v-row class="fill-height flex-column" justify="space-between">
                    <p class="text-h5 text-left pl-1 font-weight-bold" style="font-family: 'CookieRun-Regular';">{{this.title}}</p>

                    <div>
                        <p class="text-h5 ml-2 text-left" style="font-family: 'CookieRun-Regular';">TEL : {{this.tel}}</p>
                        <br>
                        <p class="text-h5 ml-2 text-left" style="font-family: 'CookieRun-Regular';">{{this.addr}}</p>
                    </div>
                </v-row>
            </v-card-title>
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
                    <p class="text-h5 text-left pl-1 font-weight-bold" style="font-family: 'CookieRun-Regular';">{{this.title}}</p>

                    <div>
                        <p class="text-h5 ml-2 text-left" style="font-family: 'CookieRun-Regular';">TEL : {{this.tel}}</p>
                        <br>
                        <p class="text-h5 ml-2 text-left" style="font-family: 'CookieRun-Regular';">{{this.addr}}</p>
                    </div>
                </v-row>
            </v-card-title>
        </div>

        <template v-slot:placeholder>
            <v-row
                class="fill-height ma-0"
                align="center"
                justify="center"
            >
                <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
            </v-row>
        </template>
    </v-card>
</template>

<script>
export default {
    name: "OverviewRecommendComp",
    props: {
        propAdd: {type: String},
        propImg: {type: String},
        propTel: {type: String},
        propTitle: {type: String}
    },
    data() {
        return {
            addr: this.propAdd,
            image: this.propImg,
            tel: this.propTel,
            title: this.propTitle
        };
    },
    created() {
        if (this.tel == "") {
            this.tel = "등록된 번호가 없습니다."
        }
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