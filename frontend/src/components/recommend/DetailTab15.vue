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
                    v-html="overview.overview"
                    style="word-spacing: 3px; line-height: 150%"
                    />
                </v-row>
            </v-col>
        </v-row>

        <v-row class="mt-2 mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔹 행사 기간 : {{info.startDate}} ~ {{info.endDate}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔸 장소 : {{info.place}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔹 소요 시간 : {{info.spendTime}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col v-html="info.useFee"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔹 연령 제한 : {{info.ageLimit}}
            </v-col>
        </v-row>
    </div>
</template>

<script>
export default {
    name: "DetailTab15",
    props: {
        propImg: {type: String},
        propItems: {type: Object}
    },
    data() {
        return {
            img: this.propImg,
            overview: this.propItems.overview,
            propInfo: this.propItems.info,
            info: [],
        }
    },
    created() {
        if (this.propInfo.ageLimit == "") {
            this.$set(this.info, 'ageLimit', '정보 없음')    
        } else this.$set(this.info, 'ageLimit', this.propInfo.ageLimit)

        if (this.propInfo.startDate == "") {
            this.$set(this.info, 'startDate', '정보 없음')    
        } else this.$set(this.info, 'startDate', this.propInfo.startDate)
        
        if (this.propInfo.endDate == "") {
            this.$set(this.info, 'endDate', "정보 없음")    
        } else this.$set(this.info, 'endDate', this.propInfo.endDate)

        if (this.propInfo.place == "") {
            this.$set(this.info, 'place', "정보 없음")    
        } else this.$set(this.info, 'place', this.propInfo.place)

        if (this.propInfo.spendTime == "") {
            this.$set(this.info, 'spendTime', "정보 없음")    
        } else this.$set(this.info, 'spendTime', this.propInfo.spendTime)

        if (this.propInfo.useFee == "") {
            this.$set(this.info, 'useFee', "🔸 이용 금액 : 정보 없음")    
        } else this.$set(this.info, 'useFee', `🔸 이용 금액 : ${this.propInfo.useFee}`)
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