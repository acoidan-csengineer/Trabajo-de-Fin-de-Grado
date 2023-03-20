<template>
  <v-container>
    <h1 class="my-5">{{$t("pois-management")}}</h1>
    <point-of-interest-component/>
  </v-container>
  
</template>

<script>
import PointOfInterestComponent from '../components/PointsOfInterest.vue'

  export default {
    name: 'PointsOfInterestView',

    components: {
      PointOfInterestComponent
    },
  }
</script>