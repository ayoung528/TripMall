import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import VueFullPage from "vue-fullpage.js";
import VueKinesis from "vue-kinesis";
import ToggleSwitch from 'vuejs-toggle-switch';

Vue.use(VueKinesis)
Vue.use(ToggleSwitch)
Vue.use(VueFullPage);
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");
