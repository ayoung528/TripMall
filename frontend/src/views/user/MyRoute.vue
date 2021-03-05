<template>
    <v-container>
        <MyRouteComp
        :propRoute="item"
        :propTitle="item.title"
        :propId="item.routeid"
        v-for="(item, i) in route"
        v-on:reload="reload"
        :key="i"
        />
        <v-row>
            <v-col class="text-right">
                <v-btn outlined class="text-h5" @click="back()">
                    <div id="text1">뒤로</div>
                </v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import MyRouteComp from "@/components/user/MyRouteComp.vue"

export default {
    name: "MyRoute",
    props: {
        propRoute: {type: Array},
    },
    components: {
        MyRouteComp
    },
    data() {
        return {
            route: this.propRoute,
        }
    },
    methods: {
        back() {
            this.$emit('back')
        },
        reload() {
            location.reload()
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