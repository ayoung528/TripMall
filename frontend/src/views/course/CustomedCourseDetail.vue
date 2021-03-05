<template>
    <v-container fluid class="pa-0">
        <CustomedCourseDetailComp
        :propItems="this.items"
        :propOverview="this.overviews.overview"
        v-on:cart="cart"
        v-if="this.items.length != 0 && this.overviews"
        />
        <v-row>
            <v-col class="text-right mr-5">
                <v-btn dark color="#9575cd"
                @click="goBack()" outlined
                style="font-size: 13px;"
                >
                    <div id="text1">뒤로</div>
                </v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import http from "@/util/http-common.js"
import CustomedCourseDetailComp from "@/components/course/CustomedCourseDetailComp.vue";

export default {
    name: "CustomedCourseDetail",
    components: {
        CustomedCourseDetailComp
    },
    props: {
        propId: {type: Number}
    },
    data() {
        return {
            items: [],
            overviews: undefined,
            id: this.propId
        }
    },
    methods: {
        goBack() {
            this.$emit('goBack')
        },
        cart() {
            this.$emit('cart')
        }
    },
    created() {
        http
        .get('recommend/getdetail', {
            params: {contentid: this.id}
        })
        .then(({ data }) => {
            this.items = data;

            http.get('info/getInfo', {
                params: {contentid: this.items[0].contentid}
            })
            .then(({data}) => {
                this.overviews = data.overview;
            })
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