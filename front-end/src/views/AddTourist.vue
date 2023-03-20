<template>
  <v-container>
    <add-tourist-component />
  </v-container>
  
</template>

<script>
import AddTouristComponent from '../components/AddTourist.vue'

  export default {
    name: 'AddTouristView',

    components: {
      AddTouristComponent,
    },
  }
</script>