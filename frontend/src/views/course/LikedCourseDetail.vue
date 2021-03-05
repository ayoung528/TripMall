<template>
    <v-container>
        <LikedCourseDetailComp
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
                    뒤로
                </v-btn>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import http from "@/util/http-common.js"
import LikedCourseDetailComp from "@/components/course/LikedCourseDetailComp.vue";

export default {
    name: "LikedCourseDetail",
    props: {
        propId: {type: Number}
    },
    components: {
        LikedCourseDetailComp
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
        .get('route/getroutedetail', {
            params: {routeid: this.id}
        })
        .then(({ data }) => {
            this.items = data;
            http.get('info/getInfo', {
                params: {contentid: this.items[0].subcontentid}
            })
            .then(({data}) => {
                this.overviews = data.overview;
            })
        })
    }
}
</script>