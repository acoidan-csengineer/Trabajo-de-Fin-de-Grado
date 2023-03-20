<template>
  <div>
    <l-map id="tourism-itinerary-map" ref="map" style="height: 300px; position: sticky !important; margin-bottom: 16px; border-radius: 20px;" :zoom="zoom" :center="center">
      <l-tile-layer :url="url"></l-tile-layer>
      <l-geo-json :geojson="geojson" :options="options"></l-geo-json>
    </l-map>
    <div id="tourismItineraryForm">
      <v-card class="mb-5">
        <v-card-title>{{ $t('multiple-itinerary-statistics') }}</v-card-title>
        <v-card-text>
          <v-row align-content-space-between>
            <v-col cols="12" sm="4">
              <div style="color: black">
                <v-icon class="mx-2" color="black" dense> mdi-palm-tree </v-icon>
                {{ $t('tourism-time')}} <b>{{tourismItinerary.timeSpentOnTourismInTotal}}</b>
              </div>
            </v-col>

            <v-col cols="12" sm="4">
              <div style="color: black">
                <v-icon class="mx-2" color="black" dense> mdi-train-car </v-icon>
                {{ $t('transport-time')}} <b>{{tourismItinerary.timeSpentOnTransportInTotal}}</b>
                </div>
            </v-col>

            <v-col cols="12" sm="4">
              <div style="color: black">
                <v-icon class="mx-2" color="black" dense> mdi-camera </v-icon>
                {{ $t('leisure-time')}} <b>{{tourismItinerary.timeSpentOnLeisureInTotal}}</b>
              </div>
            </v-col>
          </v-row>
        </v-card-text>

        <v-card-actions>
        </v-card-actions>
      </v-card>

      
      <div v-for="simpleItinerary, index in this.simpleItineraries" :key="simpleItinerary">
        <v-card class="mb-5">
          <v-card-title>{{ simpleItinerary.dayOfWeek }}</v-card-title>


          
          <v-card-text style="color: black !important;">
            <l-map id="tourism-itinerary-map" ref="map" style="height: 300px; position: sticky !important; margin-bottom: 16px; border-radius: 20px;" :zoom="zoom" :center="center">
              <l-tile-layer :url="url"></l-tile-layer>
              <l-geo-json :geojson="geojsonDays[index]" :options="options"></l-geo-json>
            </l-map>
            
            <h3 style="color: black; margin-bottom: 8px; margin-top: 8px;">{{$t('simple-itinerary-statistics')}}</h3>
            <v-row align-content-space-between style="margin-bottom: 32px;">
              <v-col cols="12" sm="4">
                <div style="color: black">
                  <v-icon class="mx-2" color="black" dense> mdi-palm-tree </v-icon>
                  {{ $t('tourism-time')}} <b>{{simpleItinerary.timeSpentOnTourism}}</b>
                </div>
              </v-col>

              <v-col cols="12" sm="4">
                <div style="color: black">
                  <v-icon class="mx-2" color="black" dense> mdi-train-car </v-icon>
                  {{ $t('transport-time')}} <b>{{simpleItinerary.timeSpentOnTransport}}</b>
                  </div>
              </v-col>

              <v-col cols="12" sm="4">
                <div style="color: black">
                  <v-icon class="mx-2" color="black" dense> mdi-camera </v-icon>
                  {{ $t('leisure-time')}} <b>{{simpleItinerary.timeSpentOnLeisure}}</b>
                </div>
              </v-col>
            </v-row>

            <h3 style="color: black; margin-bottom: 8px;">{{$t('tourism-itinerary')}}</h3>
            <div v-for="visit in simpleItinerary.visits" :key="visit">
              <div v-if="visit.transportTime >= 0" style="color: black">
                <v-icon class="mr-2" color="black" dense> mdi-clock-outline </v-icon>
                {{$t("time-next-point")}}: <b>{{ visit.transportTime }} {{$t('minutes')}}</b>
                <br>
                <br>
                <br>
              </div>

              <b>{{ visit.pointOfInterest.name }}</b>
              <br>
              <span v-if="visit.pointOfInterest.description"><p>{{ visit.pointOfInterest.description }}</p></span>
              <span v-if="visit.arrivalTime">{{$t("arrival-time")}}: <b>{{ visit.arrivalTime }}</b></span>
              <span v-if="visit.arrivalTime"><br></span>
              <span v-if="visit.departureTime">{{$t("departure-time")}}: <b>{{ visit.departureTime }}</b></span>
              <span v-if="visit.departureTime"><br></span>
              <span v-if="visit.timeOnSite">{{$t("time-on-poi")}}: <b>{{ visit.timeOnSite }}</b></span>
              <span v-if="visit.timeOnSite"><br></span>
              <span v-if="visit.pointOfInterest.averageCost">{{$t('cost')}}: <b>{{ visit.pointOfInterest.averageCost }} €</b></span>
              <span v-if="visit.pointOfInterest.averageCost"><br></span>
              <span v-if="visit.pointOfInterest.categories">{{$t('categories')}}: <b>{{ visit.pointOfInterest.categories.join(", ") }}</b></span>
              <span v-if="visit.pointOfInterest.categories"><br></span> 
              <br>
              <br>
            </div>
          </v-card-text>

          <v-card-actions>
          </v-card-actions>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import TouristService from "../services/TouristService";
import TourismItineraryService from "../services/TourismItineraryService";
import { LMap, LTileLayer, LGeoJson } from "vue2-leaflet";
import i18n from "../i18n.js";

import "leaflet/dist/leaflet.css";

import { Icon } from "leaflet";

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});

export default {
  name: "TourismItineraryComponent",
  components: {
    LMap,
    LTileLayer,
    LGeoJson,
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
  data() {
    return {
      touristService: new TouristService(),
      tourismItineraryService: new TourismItineraryService(),
      tourismItinerary: {},
      simpleItineraries: [],

      valid: false,
      dialogDelete: false,
      showAlert: false,

      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      zoom: 8,
      center: [28.4883552080302, -16.315565093647496],

      rules: {
        coordinatesRules: [
          (v) => !!v || "Las coordenadas son obligatorias",
        ],
        budgetRules: [
          v => !!v || 'El presupuesto es obligatorio',
          v => v >= 0 || 'El presupuesto debe ser mayor o igual a 0'
        ],
        departureDatetimeRules: [
          v => !!v || 'La fecha y hora de llegada al destino son obligatorias',
        ],
        returnDatetimeRules: [
          v => !!v || 'La fecha y hora del destino son obligatorias',
        ],
      },

      geojson: {
        type: "FeatureCollection",
        features: [],
      },

      geojsonDays: [],

      select: {
        id: "",
        name: "",
        surname: "",
        email: "",
        dni: "",
      },

      editedItem: {
        id: "",
        destinationCoordinates: [],
        budget: 0,
        transport: false,
        departureDatetime: '',
        returnDatetime: '',
        activities: {
          SUNANDBEACH: false,
          RURAL: false,
          GASTRONOMY: false,
          NATURE: false,
          CULTURE: false
        }
      },
    };
  },

  created() {
    this.initialize();
  },

  computed: {
    options: function() {
      return {
        onEachFeature: this.onEachFeatureFunction
      };
    },
    onEachFeatureFunction: function() {
      return (feature, layer) => {
        layer.bindTooltip(
          "<div><b>" +
            feature.properties.id + " - " + feature.properties.name +
            "</b></div><div>" + i18n.t('nombre') + ": " + feature.properties.name +
            "<br>" + i18n.t('town') + ": " + feature.properties.town + 
            "<br>" + i18n.t('postal-code') + ": " + feature.properties.postalCode + 
            "<br>" + i18n.t('categories') + ": " + feature.properties.categories + 
            "<br>" + i18n.t('applications') + ": " + feature.properties.applications + 
            "<br>" + i18n.t('horario') + ": " + feature.properties.hourly +
            "</div>",
          { permanent: false, sticky: true }
        );
      };
    }
  },

  methods: {
    initialize() {
      this.tourismItineraryService.getTourismItinerary(this.idTourist, this.idTravelPreferences).then((data) => {
        this.tourismItinerary = data.data;
        console.log(data)
        this.simpleItineraries = this.tourismItinerary.simpleItineraries;
        data.data.simpleItineraries.forEach(day => {
          let jsonPDay = JSON.parse(JSON.stringify({
            type: "FeatureCollection",
            features: [],
          }));
          
          day.visits.forEach((visit) => {
            if (visit.pointOfInterest.name == "Alojamiento") {
              return;
            }

            let jsonP = JSON.parse(JSON.stringify({
              "type": "Feature",
              "properties": {
                "id": visit.pointOfInterest.id,
                "name": visit.pointOfInterest.name,
                "description": visit.pointOfInterest.description,
                "town": visit.pointOfInterest.town,
                "postalCode": visit.pointOfInterest.postalCode,
                "categories": visit.pointOfInterest.categories,
                "applications": visit.pointOfInterest.applications,
                "hourly": visit.pointOfInterest.hourly
              },
              "geometry": {
                "type": "",
                "coordinates": []
              }
            }));

            if (visit.pointOfInterest.geolocationPoint != null) {
              jsonP.geometry = {
                "type": visit.pointOfInterest.geolocationPoint.type,
                "coordinates": visit.pointOfInterest.geolocationPoint.coordinates
              }
            } else {
              if (visit.pointOfInterest.geolocationLineString != null) {
                jsonP.geometry = {
                  "type": visit.pointOfInterest.geolocationLineString.type,
                  "coordinates": []
                }

                for(let i = 0; i < visit.pointOfInterest.geolocationLineString.coordinates.length; i++) {
                  jsonP.geometry.coordinates.push([visit.pointOfInterest.geolocationLineString.coordinates[i][0], visit.pointOfInterest.geolocationLineString.coordinates[i][1]])
                }
              } else {
                if (visit.pointOfInterest.geolocationPolygon != null) {
                  jsonP.geometry = {
                    "type": visit.pointOfInterest.geolocationPolygon.type,
                    "coordinates": [[]]
                  }

                  for(let i = 0; i < visit.pointOfInterest.geolocationPolygon.coordinates[0].coordinates.length; i++) {
                    jsonP.geometry.coordinates[0].push(visit.pointOfInterest.geolocationPolygon.coordinates[0].coordinates[i].coordinates)
                  }
                }
              }
            }

            this.geojson.features.push(JSON.parse(JSON.stringify(jsonP)));
            jsonPDay.features.push(JSON.parse(JSON.stringify(jsonP)));
          });

          this.geojsonDays.push(jsonPDay);
        });
      });
    },
  },
};
</script>

<style>
  #tourism-itinerary-map {
    position: sticky !important;
  }
</style>