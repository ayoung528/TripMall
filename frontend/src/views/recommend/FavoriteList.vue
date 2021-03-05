<template>
    <v-container>
        <v-row class="text-center">
            <v-col class="text-h4 font-weight-bold">
                <div id="text1">취향 기반 추천 스팟</div>
            </v-col>
        </v-row>
        <FavoriteListComp
        :propItems="items"
        v-on:getCart="getCart"
        v-if="this.items.length != 0"
        />
    </v-container>
</template>

<script>
import FavoriteListComp from "@/components/recommend/FavoriteListComp.vue";
import http from "@/util/http-common.js"

export default {
    name: "FavoriteList",
    components: {
        FavoriteListComp
    },
    data() {
        return {
            items: [],
        }
    },
    methods: {
        getCart() {
            this.$emit('cart')
        }
    },
    created() {
        http
        .get('userbased/getList', {
            headers: {token: this.$store.state.authToken}
        })
        .then(({data}) => {
            this.items = data
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