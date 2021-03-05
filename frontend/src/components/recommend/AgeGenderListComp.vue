<template>
    <v-container class="row justify-content-left p-3">
        <div
        class="col-12 col-sm-6 col-md-3"
        v-for="(item, index) in items"
        :key="index"
        >
            <v-card max-width="400" style="overflow:hidden;">
                <div style="height:200px;width:100%;">
                    <v-img
                    v-if="item.firstImage"
                    class="list-img white--text"
                    :src="item.firstImage"
                    @click="getDetail(item.firstImage, item.title, item.contentId)"
                    />
                    <v-img v-else
                    class="list-img white--text"
                    :src="require('@/assets/noimage.png')"
                    @click="getDetail(item.firstImage, item.title, item.contentId)"
                    >
                        <v-layout column align-center justify-center fill-height>
                            <h1 class="white--text font-weight-bold text-center" style="font-size: 35px;">No Image</h1>
                        </v-layout>
                    </v-img>
                </div>
                <v-card-title
                class="pb-0 mb-1 list-title"
                @click="getDetail(item.firstImage, item.title, item.contentId)"
                >
                   <div id="text1" style="font-size:15px;"> {{item.title}}</div>
                </v-card-title>
                <v-card-text class="text--primary">
                    <div  id="text2" class="mb-3">
                         {{item.addr1}}
                    </div>
                    <div class="d-flex justify-content-between mt-2">
                        <div class="d-flex" style="font-size:large;">
                        <v-rating
                        v-model="item.score"
                        readonly
                        background-color="orange lighten-3"
                        color="orange"
                        medium
                        />
                        </div>
                    </div>
                    <v-row class="text-right">
                        <v-col>
                            <v-btn color="purple" outlined class="text-h5" @click="setCart(item)">
                                <div id="text1"> 담기</div>
                            </v-btn>
                        </v-col>
                    </v-row>
                </v-card-text>
            </v-card>
        </div>
        <RecommendDetailComp
        :propImg="this.img"
        :propTitle="this.title"
        :propItems="this.detail"
        :propId="this.detail.overview.contentId"
        :propType="this.type"
        v-if="this.dialog"
        v-on:exit="toggle"
        />
    </v-container>
</template>

<script>
import RecommendDetailComp from "@/components/course/recommendDetailComp.vue";
import http from "@/util/http-common.js"

export default {
    name: "AgeGenderListComp",
    components: {
        RecommendDetailComp
    },
    props: {
        propItems: {type: Array}
    },
    data() {
        return {
            rating:this.propItems.score,
            dialog: false,
            items: this.propItems,
            img: '',
            title: '',
            overview: '',
            id: '',
            type: 0,
            detail: undefined,
        }
    },
    methods: {
        async getDetail(subdetailimg, subname, subid) {
            await http
            .get('/info/getInfo', {
                params: {contentid: subid}
            })
            .then(({data}) => {
                this.detail = data
                if (Object.keys(data).includes('overview')) {
                        if(data.overview.contentTypeId == 39) this.type = data.overview.contentTypeId
                        } else {
                        this.type = 0
                }
            })

            this.img = subdetailimg;
            this.title = subname;
            this.dialog = true;
        },
        toggle: function() {
            this.dialog = false;
        },
        setCart: function(item) {
            http
            .post('/cart/intocart', {
                areacode: item.areacode,
                contentId: item.contentId,
                contentTypeId: item.contentTypeId,
                latitude: item.mapx,
                longitude: item.mapy,
                title: item.title,
                userid: this.$store.state.userData.id
            })
            .then(() => {
                this.$emit('getCart')
            })
        }
    }
}
</script>

<style scoped>
.list-img {
  height: 200px !important;
  width: 100% !important;
  /* object-fit: cover; */
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}

.list-img:hover {
  transform: scale(1.1);
  transition: all 0.3s ease-in-out;
  cursor: pointer;
}

.list-title {
  cursor: pointer;
  height: 70px;
  align-items: baseline;
  text-overflow: ellipsis;
  overflow: hidden;
  line-height: 1.8rem;
}
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