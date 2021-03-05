import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

import AgeGenderList from "../views/recommend/AgeGenderList.vue";

import Login from "../views/user/login.vue";
import Mypage from "../views/user/mypage.vue";
import store from "../store";

// 에러
import err404 from "../views/errors/err404.vue";
import err500 from "../views/errors/err500.vue";
import tripEx from "../views/errors/tripEx.vue";

import cookies from "vue-cookies";
Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue")
  },
  {
    path: "/recommend/ageAndGender",
    name: "AgeGenderList",
    component: AgeGenderList
  },

  {
    path: "/user/login",
    name: "Login",
    component: Login
  },
  {
    path: "/user/mypage",
    name: "Mypage",
    component: Mypage
  },
  // 에러
  {
    path: "*",
    redirect: "/404",
  },
  {
    path: "/404",
    component: err404,
  },
  {
    path: "*",
    redirect: "/500",
  },
  {
    path: "/500",
    component: err500,
  },
  {
    path: "/tripEx",
    component: tripEx,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

router.beforeEach(function(to,from,next){
  // console.log(store.state.isLoggedin)
  // console.log(from.name)
  // console.log(to.name)

  
  if(store.state.isLoggedin&&to.name==="Home"){
    //로그인 후 url이용해 home으로 가는거 막기
    if(cookies.get("auth-token")==null){
      store.state.authToken = null;
      cookies.remove(store.state.authToken);
      store.state.isLoggedin = false;
      store.state.modal = true;
      router.push("/");
    }
    else{
      return next("/recommend/ageAndGender")
    }
    // return next("/tripEx");
    // return next(false);
  }
  if(!store.state.isLoggedin&&((from.name==null&&to.name==="Login")||(from.name=="Login"&&to.name==="AgeGenderList"))){
    //로그인 할 때 login과 로그인후 첫페이지는 이동 가능하도록 해야 함
    //그 밑에서 해당 안되면 다 막기 (오로지 로그인으로만 홈페이지 이용가능)
    return next();
  }
  if(!store.state.isLoggedin&&to.name!="Home"){
    //로그인 전 home에서 url이용해 home이 아닌 주소로 가는거 막기
    return next('/');
  }
  else{
    return next();
  }
  
});

export default router;
