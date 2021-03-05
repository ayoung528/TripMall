<template>
    <v-container>
        <MyReviewComp
        :propItem="reviews"
        v-if="this.reviews.length != 0"
        />
        <div v-else class="text-h3 mb-3 text-center"><div id="text2">아직 작성한 리뷰가 없습니다.</div>
            <div class="mt-3"><div id="text2">리뷰를 작성해서 더 정확한 추천을 받아보세요! ✍ ...</div></div>
        </div>
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
import MyReviewComp from "@/components/user/MyReviewComp.vue"
import http from "@/util/http-common.js"

export default {
    name: "MyReview",
    components: {
        MyReviewComp
    },
    data() {
        return {
            reviews: []
        }
    },
    methods: {
        back() {
            this.$emit('back')
        },
    },
    created() {
        http
        .get('/review/getByUserid', {
            params: {pageno: 0, userid: this.$store.state.userData.id}
        })
        .then(({data}) => {
            this.reviews = data.content
        })
    }
}
</script>