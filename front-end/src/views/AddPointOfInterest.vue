<template>
  <v-container>
    <add-point-of-interest-component />
  </v-container>
  
</template>

<script>
import AddPointOfInterestComponent from '../components/AddPointOfInterest.vue'

  export default {
    name: 'AddPointOfInterestView',

    components: {
      AddPointOfInterestComponent,
    },
  }
</script>