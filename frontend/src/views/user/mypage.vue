<template>
  <v-container>
        <InfoComp
        :propItem="items"
        :propCount="route_count"
        :propRoute="routeList"
        v-if="this.route_count != -1"
        />
    </v-container>
</template>

<script>
import InfoComp from "@/components/user/InfoComp.vue";
import axios from "axios";
const BACK_URL= process.env.VUE_APP_BACK_URL;

export default {
    name: "mypage",
    components: {
        InfoComp
    },
    data() {
        return {
            items: [],
            route_count: -1,
            routeList: []
        }
    },
    created() {
        axios
        .get(`${BACK_URL}/route/getmyroute`, {params : {userid:this.$store.state.userData.id}})
        .then(
          ({ data }) => {
            this.route_count=data.length
            this.routeList=data

            for(var r in this.routeList){
              axios
                .get(`${BACK_URL}/route/getroutedetail`, {params : {routeid:this.routeList[r].routeid}})
                .then(
                  ({ data }) => {
                    for(var d in data){
                      this.items.push(data[d]);
                    }
                  })
            }
          })
    }
}
</script>