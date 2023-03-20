<template>
  <v-container>
    <add-travel-preferences-component />
  </v-container>
  
</template>

<script>
import AddTravelPreferencesComponent from '../components/AddTravelPreferences.vue'

  export default {
    name: 'AddTravelPreferencesView',

    components: {
      AddTravelPreferencesComponent,
    },
  }
</script>