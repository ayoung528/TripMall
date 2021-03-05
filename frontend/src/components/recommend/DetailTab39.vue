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
                    style="word-spacing: 3px; line-height: 150%;"
                    />
                </v-row>
            </v-col>
        </v-row>

        <v-row class="mt-2 mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.infoCenter"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔹 카드 사용 유/무 : {{info.creditCard}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.openTime"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.packing"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.parking"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.repMenu"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.reservationInfo"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.restDate"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.treatMenus"
            class="text-h5 font-weight-bold"/>
        </v-row>
    </div>
</template>

<script>
export default {
    name: "DetailTab39",
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
        if (this.propInfo.creditCard == "") {
            this.$set(this.info, 'creditCard', '정보 없음')    
        } else this.$set(this.info, 'creditCard', this.propInfo.creditCard)

        if (this.propInfo.infoCenter == "") {
            this.$set(this.info, 'infoCenter', '📞 TEL : 정보 없음')    
        } else this.$set(this.info, 'infoCenter', `📞 TEL : ${this.propInfo.infoCenter}`)
        
        if (this.propInfo.openTime == "") {
            this.$set(this.info, 'openTime', "🔸 오픈 시간 : 정보 없음")    
        } else this.$set(this.info, 'openTime', `🔸 오픈 시간 : ${this.propInfo.openTime}`)

        if (this.propInfo.packing == "") {
            this.$set(this.info, 'packing', "🔹 포장 : 정보 없음")    
        } else this.$set(this.info, 'packing', `🔹 포장 : ${this.propInfo.packing}`)

        if (this.propInfo.parking == "") {
            this.$set(this.info, 'parking', "🔸 주차 정보 : 정보 없음")    
        } else this.$set(this.info, 'parking', `🔸 주차 정보 : ${this.propInfo.parking}`)

        if (this.propInfo.repMenu == "") {
            this.$set(this.info, 'repMenu', "🔹 대표 메뉴 : 정보 없음")    
        } else this.$set(this.info, 'repMenu', `🔹 대표 메뉴 : ${this.propInfo.repMenu}`)

        if (this.propInfo.reservationInfo == "") {
            this.$set(this.info, 'reservationInfo', "🔸 예약 정보 : 정보 없음")    
        } else this.$set(this.info, 'reservationInfo', `🔸 예약 정보 : ${this.propInfo.reservationInfo}`)

        if (this.propInfo.restDate == "") {
            this.$set(this.info, 'restDate', "🔹 휴일 정보 : 정보 없음")    
        } else this.$set(this.info, 'restDate', `🔹 휴일 정보 : ${this.propInfo.restDate}`)

        if (this.propInfo.treatMenus == "") {
            this.$set(this.info, 'treatMenus', "🔸 음식 메뉴 : 정보 없음")    
        } else this.$set(this.info, 'treatMenus', `🔸 음식 메뉴 : ${this.propInfo.treatMenus}`)
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