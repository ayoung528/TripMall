<template>
    <div>
        <div class="text-h4 font-weight-black"><div id="text1">상세정보 기반 추천 목록 🌅</div></div>
        <v-container v-if="overviewItems.length == 0" style="text-align: center;">
            <br>
            <div class="text-h4 text-center"><div id="text1"> (*≧∀≦*) </div></div>
            <br>
            <div class="text-h5 text-center"><div id="text2">추천될 목록이 없습니다.</div></div>
        </v-container>
        <v-sheet v-else class="mx-auto mb-3" max-width="800">
            <div class="text-h5 mt-1 pl-1"><div id="text2">{{overviewItems.length}} 개의 추천목록이 있습니다.</div></div>
            <v-slide-group class="pa-4" show-arrows>
                <v-slide-item v-for="item in overviewItems" :key="item.contentId">
                    <OverviewRecommendComp
                    :propAdd="item.addr1"
                    :propImg="item.firstImage"
                    :propTel="item.tel"
                    :propTitle="item.title"
                    />
                </v-slide-item>
            </v-slide-group>
        </v-sheet>

        <div class="text-h4 font-weight-black"><div id="text1">가까운 거리순 추천 목록 🚌</div></div>
        <v-container v-if="distanceItems.length == 0" style="text-align: center;">
            <br>
            <div class="text-h4 text-center"> <div id="text1">(*≧∀≦*) </div></div>
            <br>
            <div class="text-h5 text-center"><div id="text2">추천될 목록이 없습니다.</div></div>
        </v-container>
        <v-sheet v-else class="mx-auto mb-3" max-width="800">
            <div class="text-h5 mt-1 pl-1"><div id="text2">{{distanceItems.length}} 개의 추천목록이 있습니다.</div></div>
            <v-slide-group class="pa-4" show-arrows>
                <v-slide-item v-for="item in distanceItems" :key="item.contentId">
                    <MenuRecommendComp
                    :propAdd="item.addr1"
                    :propImg="item.firstImage"
                    :propTel="item.tel"
                    :propTitle="item.title"
                    />
                </v-slide-item>
            </v-slide-group>
        </v-sheet>
        
        <div class="text-h4 font-weight-black"><div id="text1">관련된 음식점 추천 목록 🍱</div></div>
        <v-container v-if="items.length == 0" style="text-align: center;">
            <br>
            <div class="text-h4 text-center"> <div id="text1">(*≧∀≦*)</div> </div>
            <br>
            <div class="text-h5 text-center"><div id="text2">추천될 목록이 없습니다.</div></div>
        </v-container>
        <v-sheet v-else class="mx-auto mb-3" max-width="800">
            <div class="text-h5 mt-1 pl-1"><div id="text2">{{items.length}} 개의 추천목록이 있습니다.</div></div>
            <v-slide-group class="pa-4" show-arrows>
                <v-slide-item v-for="item in items" :key="item.contentId">
                    <MenuRecommendComp
                    :propAdd="item.addr1"
                    :propImg="item.firstImage"
                    :propTel="item.tel"
                    :propTitle="item.title"
                    />
                </v-slide-item>
            </v-slide-group>
        </v-sheet>
    </div>
</template>

<script>
import MenuRecommendComp from "@/components/course/MenuRecommendComp.vue";
import OverviewRecommendComp from "@/components/course/OverviewRecommendComp.vue"

export default {
    name: "recommendRelationComp",
    components: {
        MenuRecommendComp,
        OverviewRecommendComp
    },
    props: {
        propItem: {type: Array},
        propOverview: {type: Array},
        propDistance: {type: Array}
    },
    data() {
        return {
            items: this.propItem,
            overviewItems: this.propOverview,
            distanceItems: this.propDistance
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