<template>
  <div class="home">
    <full-page :options="options" id="fullpage">
      <div class="section">
        <v-img :src="require('@/assets/MBL.gif')" style="height:100%;">
        <div class="container" style="margin-top:20%">
         <kinesis-container>
              
              <kinesis-element :strength="50" class="title">
                <h1>T</h1>
              </kinesis-element>
              <kinesis-element :strength="40" class="title">
                <h1>r</h1>
              </kinesis-element>
              <kinesis-element :strength="30" class="title">
                <h1>i</h1>
              </kinesis-element>
              <kinesis-element :strength="20" class="title">
                <h1>p</h1>
              </kinesis-element>
              <kinesis-element :strength="-20" class="title" style="margin-left:30px;">
                <h1>M</h1>
              </kinesis-element>
              <kinesis-element :strength="-30" class="title">
                <h1>a</h1>
              </kinesis-element>
              <kinesis-element :strength="-40" class="title">
               <h1>l</h1>
              </kinesis-element>
              <kinesis-element :strength="-50" class="title">
               <h1>l</h1>
              </kinesis-element>
              
              <div id="kakaologin">
              <!-- <v-img id="img1" src="../assets/T_logo.png" /> -->
              <a :href="getUrl()">
                <v-img src="../assets/kakaologin.png"/>
              </a>
              </div>
              <div id="mouse" >
                <v-img src="../assets/mouse.png"/>
              </div>
          </kinesis-container>
        </div>
        </v-img>
      </div>
      <div class="section">
        <v-img src="../assets/MAL.gif" style="height:100%;">
        <div class="bubble_no">
        </div>
        <div class="bubble_text">
          한눈에 알아보기
        </div>
        <div class="line">
        </div>
        <v-carousel
          cycle
          hide-delimiter-background
          show-arrows-on-hover
        >
        
          <v-carousel-item
            v-for="(slide, i) in slides"
            :key="i"
          >
            <v-sheet
              :color="colors[i]"
              height="100%"
            >
              <v-row
                class="fill-height"
                align="center"
                justify="center"
              >
                <div class="display-3">
                  <img :src="slide.src" style="height:500px; weith:500px;"/>
                </div>
              </v-row>
            </v-sheet>
          </v-carousel-item>
        </v-carousel>
        <div id="mouse1" >
            <v-img src="../assets/mouse.png"/>
        </div>
        </v-img>
      </div>
      <div class="section">
        <v-img src="../assets/MAL.gif" style="height:100%;">
        <div id="myvideo" style="display: inline-block;">
        <video class="ml-16 mt-10" ref="myvideo"
        src="../assets/page3_video.mp4"
        loop
        muted
        autoplay
        width="1100"></video>
        </div>
        <div class="mt-10 mr-10" style="display: inline-block; float:right;">
          <v-sheet
          class="rounded-xl"
          elevation="10"
          height="620"
          width="300"
          style="border: 7px solid black; background-color: rgba( 255, 255, 255, 0.5 );"
          >
            <v-img src="../assets/page3_1.png" />
            <button class="videobtn" :disabled="isPlaying" @click="play"><span class="wave"></span>Play</button>
            <button class="videobtn" :disabled="!isPlaying" @click="stop"><span class="wave"></span>Stop</button>
          </v-sheet>
        </div>
        <div id="mouse1" >
            <v-img src="../assets/mouse.png"/>
        </div>
        </v-img>
      </div>
      <div class="section">
        <v-img src="../assets/Login.gif" style="height:100%;">
        <div id="kakaologo">
        <a :href="getUrl()">
          <v-img src="../assets/kakaotalk.png"/>
        </a>
        </div>
        <h1 id="jointitle">같이 한 번 시작해봐요 :)</h1>
        </v-img>
      </div>
    </full-page>
  </div>
</template>

<script>
const BACK_URL = process.env.VUE_APP_BACK_URL;

export default {
  name: "Home",
  components : {
  },
  data() {
    return {
      isPlaying: true,
      options: {
        licenseKey: "YOUR_KEY_HERE",
        afterLoad: this.afterLoad,
        scrollOverflow: true,
        scrollBar: false,
        menu: "#menu",
        navigation: true,
        sectionsColor: ["#fff59d", "#fff59d","#fff59d", "#fff59d","#fff59d", "#fff59d"]
      },
      colors: [
        'rgb(255,240,100)',
        'rgb(255,240,100)',
        'rgb(255,240,100)',
        'rgb(255,240,100)',
        // 'rgb(221, 160, 221)',
      ],
      slides: [
        {
          src:require("../assets/page2.png")
        },
        {
          src:require("../assets/page3.png")
        },
        {
          src:require("../assets/freetrip.png")
        }
      ]
    };
  },
  mounted(){
    history.pushState(null, null, location.href);
    window.onpopstate = function() {
      history.go(1);
    };
  },
  methods: {
    getUrl() {
      return `https://kauth.kakao.com/oauth/authorize?client_id=ab3521b315b3fab3db8d8ad5eeaec18e&redirect_uri=${BACK_URL}/account/kakaoLogin&response_type=code`
    },
    play() {
    	this.$refs.myvideo.play()
      this.isPlaying = true
    },
    stop() {
    	this.$refs.myvideo.pause()
      this.isPlaying = false
    }
  },
  created() {
    this.$refs.myvideo.play()
  }
};
</script>
<style>
html { font-size: 62.5%; }
@media screen and (max-width: 1300px) {
  html { font-size: 52.5%; }
}
@media screen and (max-width: 800px) {
  html { font-size: 42.5%; }
}
@media screen and (max-width: 650px) {
  html { font-size: 32.5%; }
}
@media screen and (max-width: 500px) {
  html { font-size: 22.5%; }
}
@media screen and (max-width: 400px) {
  html { font-size: 12.5%; }
}
@media screen and (max-width: 200px) {
  html { font-size: 8.5%; }
}
.container{
  text-align: center;
}
.title { 
  display: inline-block;
  text-align: center;
  /* color:rgb(252, 172, 252); */
  color:rgb(253, 93, 253);
  margin: o.1rem;
  text-shadow: 0.5rem 0.5rem 1rem #666666;
  padding-top: 0.5rem;
}
.title h1 {
  font-family: 'Gorditas', cursive;
  font-size:20rem;
  /* -webkit-text-stroke: 5px #c83cc8ff; */
}
#img1 {
  width: 7%;
  height: 7%;
  margin:0 auto;
}
#kakaologo {
  width: 100px;
  height: 100px;
  margin: 0 auto;
  margin-top: 580px;
}
#kakaologin {
  width: 6%;
  height: 3%;
  margin: 0 auto;
  margin-top: 5%;
  
}
#jointitle {
  font-family: 'CookieRun-Regular';
  text-align: center;
  padding-top:1%
}
.bubble_no {
  width: 50%;
  height: 5%;
}
.bubble_text {
  font-family: 'CookieRun-Regular';
  background-color: rgb(255,240,100);
  color: #000;
  max-width: 500px;
  margin: 0 auto;
  padding: 15px;
  border-radius: 10px;
  text-align: center;
  font-size: 300%;
  width: 50%;
  height: 10%;
}
.line {
  width: 5px;
  height: 30px;
  background-color:rgb(255,240,100);
  margin: 0 auto;
}
@font-face {
    font-family: 'TmoneyRoundWindExtraBold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/TmoneyRoundWindExtraBold.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
#mouse {
  margin: 0 auto;
  margin-top: 10%;
  bottom: 0;
  width: 10%;
  height: 10%;
}
#mouse1 {
  margin: 0 auto;
  bottom: 0;
  width: 6%;
  height: 6%;
}
.videobtn {
  margin-left: 10%;
  display: inline-block;
  width: 100px;
  height: 61px;
  position: relative;
  font-size: 20px;
  cursor: pointer;
  padding: .7em 0.5em;
  /* border: 3px solid #333; */
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 1px 3px rgba(black, .2);
  background: lighten(#f0f4c3, 10%);
  transform: translate3d(0, 0, 0);
  font-family: 'TmoneyRoundWindExtraBold';
}


.wave {
  opacity: .4;
  position: absolute;
  bottom: 3%;
  left: 50%;
  background: rgb(232, 71, 247);
  width: 300px;
  height: 300px;
  margin-left: -150px;
  margin-bottom: -301px;
  transform-origin: 50% 48%;
  border-radius: 43%;
  
  transition-duration: 0.75s;
  transition-property: margin-bottom;
  transition-timing-function: ease;
}

button:hover .wave {
  margin-bottom: -250px;
  animation: rotate 2000ms infinite linear;
}

@keyframes rotate {
  from { transform: rotate(0deg); }
  from { transform: rotate(360deg); }
}
@font-face {
    font-family: 'CookieRun-Regular';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/CookieRun-Regular.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
</style>