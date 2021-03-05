<template>
    <div>
        <v-img
        src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
        max-height="200"
        >
        </v-img>
        <v-card-text class="font-weight-bold text-center">
            청주대학교
        </v-card-text>
        <v-card-text class="text-center">
            충북 청주시 우암동
        </v-card-text>
        <v-row>
            <v-col>
                <v-rating
                v-model="rating"
                readonly
                background-color="orange lighten-3"
                color="orange"
                medium
                />
            </v-col>
            <v-col class="text-right">
                리뷰 · 47개
            </v-col>
        </v-row>
    </div>
</template>

<script>
export default {
    name: "DistanceTab",
    data: function() {
        return {
            rating: 3,
        }
    }
}
</script>