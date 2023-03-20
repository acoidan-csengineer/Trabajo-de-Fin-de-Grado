<template>
  <v-container>
    <h1 class="my-5">{{$t("tourism-itinerary")}}</h1>
    <tourism-itinerary-component :idTourist="idTourist" :idTravelPreferences="idTravelPreferences"/>
  </v-container>
  
</template>

<script>
  import TourismItineraryComponent from '../components/TourismItinerary.vue'
  export default {
    name: 'TourismItineraryView',

    components: {
      TourismItineraryComponent
    },

    props: {
      idTourist: {
        type: String,
        required: true
      },
      idTravelPreferences: {
        type: String, 
        required: true
      }
    },
  }
</script>