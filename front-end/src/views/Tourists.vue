<template>
  <v-container>
    <h1 class="my-5">{{$t("tourists-management")}}</h1>
    <tourists-component />
  </v-container>
  
</template>

<script>
import TouristsComponent from '../components/Tourists.vue'

  export default {
    name: 'TouristsView',

    components: {
      TouristsComponent,
    },
  }
</script>