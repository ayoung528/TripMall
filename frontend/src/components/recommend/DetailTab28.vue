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
            <v-col
            v-html="info.tel"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔹 카드 사용 유/무 : {{info.credit}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.parking"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔹 애완동물 가능 유/무 : {{info.pet}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔸 휴무일 : {{info.restDate}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.useTime"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔸 연령 제한 : {{info.ageRange}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔹 수용 인원 : {{info.capacity}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col class="text-h5 font-weight-bold">
                🔸 주차 요금 : {{info.parkingFee}}
            </v-col>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.reservationInfo"
            class="text-h5 font-weight-bold"/>
        </v-row>
        <v-row class="mr-2 ml-2 mb-0" style="background-color: #e991fdb3">
            <v-col
            v-html="info.useFee"
            class="text-h5 font-weight-bold"/>
        </v-row>
    </div>
</template>

<script>
export default {
    name: "DetailTab32",
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
            this.$set(this.info, 'credit', '정보 없음')    
        } else this.$set(this.info, 'credit', this.propInfo.creditCard)
        
        if (this.propInfo.infoCenter == "") {
            this.$set(this.info, 'tel', "📞 TEL : 정보 없음")    
        } else this.$set(this.info, 'tel', `📞 TEL : ${this.propInfo.infoCenter}`)
        
        if (this.propInfo.parking == "") {
            this.$set(this.info, 'parking', "🔸 주차장 유/무 : 정보 없음")    
        } else this.$set(this.info, 'parking', `🔸 주차장 유/무 : ${this.propInfo.parking}`)

        if (this.propInfo.useTime == "") {
            this.$set(this.info, 'useTime', "🔹 이용 가능 시간 : 정보 없음")    
        } else this.$set(this.info, 'useTime', `🔹 이용 가능 시간 : ${this.propInfo.useTime}`)

        if (this.propInfo.pet == "") {
            this.$set(this.info, 'pet', "정보 없음")    
        } else this.$set(this.info, 'pet', this.propInfo.pet)

        if (this.propInfo.restDate == "") {
            this.$set(this.info, 'restDate', "정보 없음")    
        } else this.$set(this.info, 'restDate', this.propInfo.restDate)

        if (this.propInfo.ageRange == "") {
            this.$set(this.info, 'ageRange', "정보 없음")    
        } else this.$set(this.info, 'ageRange', this.propInfo.ageRange)

        if (this.propInfo.capacity == "") {
            this.$set(this.info, 'capacity', "정보 없음")    
        } else this.$set(this.info, 'capacity', this.propInfo.capacity)

        if (this.propInfo.parkingFee == "") {
            this.$set(this.info, 'parkingFee', "정보 없음")    
        } else this.$set(this.info, 'parkingFee', this.propInfo.parkingFee)

        if (this.propInfo.reservationInfo == "") {
            this.$set(this.info, 'reservationInfo', "🔹 예약 정보 : 정보 없음")    
        } else this.$set(this.info, 'reservationInfo', `🔹 예약 정보 : ${this.propInfo.reservationInfo}`)

        if (this.propInfo.useFee == "") {
            this.$set(this.info, 'useFee', "🔸 이용 요금 : 정보 없음")    
        } else this.$set(this.info, 'useFee', `🔸 이용 요금 : ${this.propInfo.useFee}`)
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