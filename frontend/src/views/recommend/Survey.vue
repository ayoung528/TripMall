<template>
    <v-container>
        <SurveyComp
        v-if="this.items !== true && this.items.length != 0"
        :propItems="items"
        v-on:app="goMain"
        />
        <FavoriteList
        v-if="this.items === true"
        v-on:cart="getCart"
        />
    </v-container>
</template>

<script>
import SurveyComp from "@/components/recommend/SurveyComp.vue"
import FavoriteList from "@/views/recommend/FavoriteList.vue"
import http from "@/util/http-common.js"
import { mapState} from "vuex"

export default {
    name: "Survey",
    components: {
        SurveyComp,
        FavoriteList
    },
    data() {
        return {
            items: [],
            realItems: [],
        }
    },
    methods: {
        getCart() {
            this.$emit('cart')
        },
        goMain() {
            this.$emit('reload')
        }
    },
    computed: {
        ...mapState(["isLoggedin", "userData"]),
    },
    created() {
        http.get('/userbased/getSurveyList', {
            params: {userid: this.$store.state.userData.id}
        })
        .then(({data}) => {
            this.items = data
        })
    }
}
</script>