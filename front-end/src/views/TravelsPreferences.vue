<template>
  <v-container>
    <h1 class="my-5">{{$t("travels-preferences-management")}}</h1>
    <travels-preferences-component />
  </v-container>
  
</template>

<script>
import TravelsPreferencesComponent from '../components/TravelsPreferences.vue'

  export default {
    name: 'TravelsPreferencesView',

    components: {
      TravelsPreferencesComponent,
    },
  }
</script>