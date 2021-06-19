<template>
  <v-app >
    <v-main>
      <div v-if="alert.message" :class="`alert ${alert.type}`">{{alert.message}}</div>
        <router-view/>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: 'App',
  data: () => ({
  }),
  computed: {
    alert () {
        return this.$store.state.alert
    }
  },
  watch:{
    $route (to, from){
        // clear alert on location change
        this.$store.dispatch('alert/clear');
    }
  }
};
</script>
