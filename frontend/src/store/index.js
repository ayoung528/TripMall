import Vue from "vue";
import Vuex from "vuex";
// import axios from "axios";
import cookies from "vue-cookies";
import router from "../router";

Vue.use(Vuex);
// const BACK_URL = process.env.VUE_APP_BACK_URL;
import Swal from 'sweetalert2';
// import { Base64 } from 'js-base64';
import createPersistedState from "vuex-persistedstate";

export default new Vuex.Store({
  state: {
    // authToken: cookies.get("auth-token"),
    authToken: "",
    isLoggedin: false,
    userData: {
      id:"",
      email:"",
      nickname:"",
      profileImage:"",
      gender:"",
      age_range:"",
      create_time:"",
      review_count:"",
    },
    cartList:[],
  },
  mutations: {
    SET_TOKEN(state, token) {
      router.push("/recommend/ageAndGender");
      state.authToken = token;
      cookies.set("auth-token", token, "1d");
      state.isLoggedin = true;
      console.log(token)
        Swal.fire({
          icon: 'success',
          height: 500,
          width: 400,
          title : '로그인 되셨습니다.',
          text : '환영합니다.'
        })
    },
    REMOVE_TOKEN(state) {
      state.authToken = null;
      cookies.remove("auth-token");
      state.isLoggedin = false;
      state.modal = true;
      router.push("/");
    },
    USERDATA() {
      var tokendata = this.state.authToken.split(".")
      var payload = tokendata[1]
      var decoding = atob(payload)
      var json=JSON.parse(decoding)
      this.state.userData.id=json.user.id
    },
  },
  actions: {
    async setList2({commit}, params){
      const items = params.map(d => ({
        id: d.contentId,
        title: d.title,
        lat: d.latityde,
        lng: d.longitude,
        type: d.contentTypeId,
      }));
      commit("setList2", items);
    },

  },
  modules: {},
  plugins: [
    createPersistedState()
  ]

});
