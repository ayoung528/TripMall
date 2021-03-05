<template>
  <div>
  </div>
</template>

<script>
import { mapMutations} from 'vuex'
export default {
    methods:{
        ...mapMutations(["SET_TOKEN","USERDATA"]),
        // ...mapActions(["UserData"])
    },
    created:function(){
        this.SET_TOKEN(this.$route.query.token)
        this.USERDATA()
    }
}
</script>

<style>

</style>