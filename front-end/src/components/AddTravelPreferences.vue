<template>
  <div>
    <div id="travelsPreferencesForm">
      <v-card class="mb-5">
        <v-card-title>{{ $t('add-travel-preferences') }}</v-card-title>
        <v-card-text>
          <v-form v-model="valid" ref="form" lazy-validation>
            <v-row>
              <v-col cols="12">
                <v-select
                  v-model="select"
                  :hint="`${select.name} ${select.surname} (${select.dni})`"
                  :items="tourists"
                  item-text="name"
                  item-value="id"
                  :label="$t('selection-tourist')"
                  return-object
                  solo
                ></v-select>
              </v-col>

              <v-col cols=12>
                <div class="mb-3">{{$t('hotel-area')}}</div>
                <l-map id="poi-map" ref="map" style="height: 300px; position: sticky !important;" :zoom="zoom" :center="center" @click="loadLonLat">
                  <l-tile-layer :url="url"></l-tile-layer>
                  <l-marker :lat-lng="editedItem.destinationCoordinates" v-if = "editedItem.destinationCoordinates.length > 0"></l-marker>
                </l-map>
              
                <v-row>
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model.number="editedItem.destinationCoordinates[0]"
                      :rules="rules.coordinatesRules"
                      :label="$t('latitude')"
                      type="number"
                      step="0.01"
                      required
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model.number="editedItem.destinationCoordinates[1]"
                      :rules="rules.coordinatesRules"
                      :label="$t('longitude')"
                      type="number"
                      step="0.01"
                      required
                    ></v-text-field>
                  </v-col>    
                </v-row>
              </v-col>

              <v-col cols="12">
                <v-row>
                  <v-col cols="12" md="4">
                    <v-text-field
                      v-model="editedItem.budget"
                      :label="$t('budget')"
                      :rules="rules.budgetRules"
                      type="number"
                      min="0"
                      step="100"
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" md="4">
                    <v-checkbox
                      v-model="editedItem.transport"
                      :label="$t('transport')"
                    ></v-checkbox>
                  </v-col>
                </v-row>
              </v-col>
              

              <v-col cols="12">
                <v-row>
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="editedItem.departureDatetime"
                      :label="$t('departureDatetime')"
                      :rules="rules.departureDatetimeRules"
                      type="datetime-local"
                    ></v-text-field>
                  </v-col>
                  
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="editedItem.returnDatetime"
                      :label="$t('returnDatetime')"
                      :rules="rules.returnDatetimeRules"
                      type="datetime-local"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-col>

              <v-col cols="12">
                <v-row>
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="editedItem.tourismStartTime"
                      :label="$t('tourism-start-time')"
                      :rules="rules.tourismStartTimeRules"
                      type="time"
                    ></v-text-field>
                  </v-col>
                  
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="editedItem.tourismEndTime"
                      :label="$t('tourism-end-time')"
                      :rules="rules.tourismEndTimeRules"
                      type="time"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-col>

              <v-col cols="12">
                {{ $t("activities") }}
              </v-col>

              <v-col cols="12" md="3">
                <v-checkbox
                  v-model="editedItem.activities.SUNANDBEACH"
                  :label="$t('sun-and-beach')"
                ></v-checkbox>
              </v-col>

              <v-col cols="12" md="3">
                <v-checkbox
                  v-model="editedItem.activities.RURAL"
                  :label="$t('rural')"
                ></v-checkbox>
              </v-col>

              <v-col cols="12" md="3">
                <v-checkbox
                  v-model="editedItem.activities.NATURE"
                  :label="$t('nature')"
                ></v-checkbox>
              </v-col>

              <v-col cols="12" md="3">
                <v-checkbox
                  v-model="editedItem.activities.GASTRONOMY"
                  :label="$t('gastronomy')"
                ></v-checkbox>
              </v-col>

              <v-col cols="12" md="3">
                <v-checkbox
                  v-model="editedItem.activities.CULTURE"
                  :label="$t('culture')"
                ></v-checkbox>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="red"  class="white--text" @click="back"> 
            {{ $t('cancel') }}
          </v-btn>
          <v-btn color="white" @click="add">
            {{ $t('add') }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </div>


    <v-alert type="success" v-if="showAlert">{{$t('travel-preferences-created')}}</v-alert>
  </div>
</template>

<script>
import TouristService from "../services/TouristService";
import { LMap, LTileLayer, LMarker } from "vue2-leaflet";
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
  name: "AddTravelPreferencesComponent",
  components: {
    LMap,
    LTileLayer,
    LMarker
  },
  data() {
    return {
      touristService: new TouristService(),
      tourists: [],

      valid: false,
      dialogDelete: false,
      showAlert: false,

      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      zoom: 8,
      center: [28.4883552080302, -16.315565093647496],

      rules: {
        coordinatesRules: [
          (v) => !!v || i18n.t('travels-preferences-rules.coordinatesRules'),
        ],
        budgetRules: [
          v => !!v || i18n.t('travels-preferences-rules.budgetRules1'),
          v => v >= 0 || i18n.t('travels-preferences-rules.budgetRules2')
        ],
        departureDatetimeRules: [
          v => !!v || i18n.t('travels-preferences-rules.departureDatetimeRules'),
        ],
        returnDatetimeRules: [
          v => !!v || i18n.t('travels-preferences-rules.returnDatetimeRules1'),
          v => {
            let returnDate = new Date(v);

            if (returnDate - new Date(this.editedItem.departureDatetime) < 86400000) {
              return i18n.t('travels-preferences-rules.returnDatetimeRules2');
            }

            return true;
          }
        ],
      },

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
        tourismStartTime: '',
        tourismEndTime: '',
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

  methods: {
    initialize() {
      this.touristService.getAll().then((data) => {
        this.tourists = data.data;
      });
    },

    loadLonLat(event) {
      this.editedItem.destinationCoordinates.splice(0);
      this.editedItem.destinationCoordinates.push(event.latlng.lat, event.latlng.lng);      
    },

    back() {
        let ruta = (this.$route.path).substr(0, (this.$route.path).length-4)
        this.$router.push({path: ruta})
        this.$router.go();
    },

    add() {
      let validacion = this.$refs.form.validate();

      if (validacion == true) {
        let travelPreferencesP = {
            id: "",
            destinationGeolocation: {
              type: "Point",
              coordinates: [this.editedItem.destinationCoordinates[1], this.editedItem.destinationCoordinates[0]]
            },
            budget: this.editedItem.budget,
            activities: [],
            transport: false,
            departureDatetime: this.editedItem.departureDatetime,
            returnDatetime: this.editedItem.returnDatetime,
            tourismStartTime: this.editedItem.tourismStartTime,
            tourismEndTime: this.editedItem.tourismEndTime
          }

        for (let activitie in this.editedItem.activities) {
          if (this.editedItem.activities[activitie] == true) {
            travelPreferencesP.activities.push(activitie)
          }
        }

        if (this.editedItem.transport == true) {
          travelPreferencesP.transport = true;
        }

        travelPreferencesP.id = this.tourists[this.tourists.indexOf(this.select)].travelsPreferences.length+1;
        this.touristService.addTravelPreferences(this.select.id, travelPreferencesP).then((data) => {
          if (data.status == 201) {
            this.showAlert = "true";
            setTimeout(() => {this.back()}, 2000);
          } else {
            console.error(data);
          }
        });
      }
    },
  },
};
</script>

<style>
/* Con scope el estilo solo afecta a este componente */
.v-data-table-header {
  background-color: #77c9d4;
}
</style>