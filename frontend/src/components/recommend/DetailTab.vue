<template>
    <div>
        <v-img
        v-if="this.img"
        :src="this.img"
        max-height="300"
        />
        <v-img
        v-else
        :src="require('@/assets/noimage.png')"
        max-height="300"
        >
            <v-layout column align-center justify-center fill-height>
                <h1 class="white--text font-weight-bold text-center" style="font-size: 35px;">No Image</h1>
            </v-layout>
        </v-img>

        <v-row class="ma-2" style="background-color: #fff06491">
            <v-col>
                <v-row>
                    <v-col class="text-h5 text-left font-weight-bold">
                        <div id="text1">💁 코스 설명</div>
                    </v-col>
                </v-row>
                <v-row class="ma-1">
                    <v-col
                    class="text-h6 text-left pa-2 font-weight-bold"
                    v-html="overview"
                    style="word-spacing: 3px; line-height: 150%;"
                    />
                </v-row>
            </v-col>
        </v-row>
    </div>
</template>

<script>
export default {
    name: "DetailTab",
    props: {
        propImg: {type: String},
        propOverview: {type: String}
    },
    data() {
        return {
            img: this.propImg,
            overview: this.propOverview,
        }
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