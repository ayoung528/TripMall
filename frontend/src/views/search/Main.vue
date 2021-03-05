<template>
    <v-container>
        <v-row class="text-center">
            <v-col>
                <toggle-switch style="font-family: 'CookieRun-Regular';"
                :options="myOptions"
                v-model="isCourse"
                />
            </v-col>
        </v-row>
        <SpotListComp
        v-if="isSearch"
        v-on:getCart="getCart"
        />
        <MakeCourseComp
        v-if="!isSearch && this.items.length != 0"
        :propItems="items"
        :propArea="area"
        v-on:empty-cart="empty"
        />
        <NoCartComp
        v-if="!isSearch && this.items.length == 0"
        />
    </v-container>
</template>

<script>
import SpotListComp from "@/components/search/SpotListComp.vue";
import MakeCourseComp from "@/components/search/MakeCourseComp.vue";
import NoCartComp from "@/components/search/NoCartComp.vue";
import http from "@/util/http-common.js";

export default {
    name: "Main",
    components: {
        SpotListComp,
        MakeCourseComp,
        NoCartComp
    },
    data() {
        return {
            isCourse: '검색',
            myOptions: {
                layout: {
                    color: '#9575cd',
                    backgroundColor: 'white',
                    selectedColor: 'white',
                    selectedBackgroundColor: '#9575cd',
                    borderColor: '#9575cd',
                    squareCorners: false,
                    noBorder: false,
                },
                size: {
                    width: 30
                },
                items: {
                    delay: .2,
                    disabled: false,
                    labels: [
                        {name: '검색'},
                        {name: '코스 만들기'}
                    ],
                },
            },
            items: [],
            area: []
        }
    },
    methods: {
      getCart: function() {
        this.cartLoad()
        this.$emit('cart')
      },
      empty: function() {
        this.$emit('empty-cart')
      },
      cartLoad() {
        http
        .get('/cart/getcart', {
          headers: {token: this.$store.state.authToken},
          params: {userid: this.$store.state.userData.id}
        })
        .then(({data}) => {
          this.items = data
          for (var i = 0; i < this.items.length; i++) {
            if (this.items[i].areacode == 1) {
              this.$set(this.items[i], 'area', '서울')
            }
            else if (this.items[i].areacode == 2) {
              this.$set(this.items[i], 'area', '인천')
            }
            else if (this.items[i].areacode == 3) {
              this.$set(this.items[i], 'area', '대전')
            }
            else if (this.items[i].areacode == 4) {
              this.$set(this.items[i], 'area', '대구')
            }
            else if (this.items[i].areacode == 5) {
              this.$set(this.items[i], 'area', '광주')
            }
            else if (this.items[i].areacode == 6) {
              this.$set(this.items[i], 'area', '부산')
            }
            else if (this.items[i].areacode == 7) {
              this.$set(this.items[i], 'area', '울산')
            }
            else if (this.items[i].areacode == 8) {
              this.$set(this.items[i], 'area', '세종')
            }
            else if (this.items[i].areacode == 31) {
              this.$set(this.items[i], 'area', '경기')
            }
            else if (this.items[i].areacode == 32) {
              this.$set(this.items[i], 'area', '강원')
            }
            else if (this.items[i].areacode == 33) {
              this.$set(this.items[i], 'area', '충북')
            }
            else if (this.items[i].areacode == 34) {
              this.$set(this.items[i], 'area', '충남')
            }
            else if (this.items[i].areacode == 35) {
              this.$set(this.items[i], 'area', '경북')
            }
            else if (this.items[i].areacode == 36) {
              this.$set(this.items[i], 'area', '경남')
            }
            else if (this.items[i].areacode == 37) {
              this.$set(this.items[i], 'area', '전남')
            }
            else if (this.items[i].areacode == 38) {
              this.$set(this.items[i], 'area', '전북')
            }
            else if (this.items[i].areacode == 39) {
              this.$set(this.items[i], 'area', '제주')
            }
          }
        })
      },
    },
    created() {
        this.cartLoad()
    },
    computed: {
      isSearch() {
        if (this.isCourse == '검색') {
          return this.$emit('toggle')
        }
        else {
          this.cartLoad()
          return false
        }
      }
    }
}
</script>
<style>
toggle-switch{
    font-family: 'CookieRun-Regular';
}
@font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>