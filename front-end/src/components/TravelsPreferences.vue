<template>
  <div>
    <v-btn class="mb-5" color="white" :href="path + '/add'">
      <v-icon
        left
        medium
      >
        mdi-plus
      </v-icon>
      {{$t('add-travel-preferences')}}
    </v-btn>


    <div id="travelsPreferencesForm">
      <v-card class="mb-5 br-10px" v-if="!hideForm">
        <v-card-title>{{ $t('modify-travel-preferences') }}</v-card-title>
        <v-card-text>
          <v-form v-model="valid" ref="form" lazy-validation>
            <v-row>
              <v-col cols=12>
                <div class="mb-3">{{$t('hotel-area')}}</div>
                <l-map id="poi-map" ref="map" style="height: 300px; position: sticky !important;" :zoom="zoom" :center="center" @click="loadLonLat">
                  <l-tile-layer :url="url"></l-tile-layer>
                  <l-geo-json :geojson="editedItem.destinationGeolocation"></l-geo-json>
                </l-map>
              
                <v-row>
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model.number="editedItem.destinationGeolocation.coordinates[1]"
                      :rules="rules.coordinatesRules"
                      :label="$t('latitude')"
                      type="number"
                      step="0.01"
                      required
                    ></v-text-field>
                  </v-col>

                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model.number="editedItem.destinationGeolocation.coordinates[0]"
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
          <v-btn color="red"  class="white--text" @click="close"> 
            {{ $t('cancel') }}
          </v-btn>
          <v-btn color="white" @click="modify">
            {{ $t('modify') }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </div>

    <div id="touristsTable">
      <v-data-table
        :headers="headers"
        :items="travelsPreferences"
        :items-per-page="10"
        sort-by="id"
        class="elevation-10"
      >
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon small class="mr-2" @click="editItem(item)">
            mdi-pencil
          </v-icon>
          <v-icon small @click="createTourismItinerary(item)">
            mdi-airplane-marker
          </v-icon>
          <v-icon small @click="deleteItem(item)">
            mdi-delete
          </v-icon>
        </template>
        <template v-slot:no-data>
          <v-btn color="primary" @click="initialize"> Reset </v-btn>
        </template>
      </v-data-table>

      <v-dialog v-model="dialogDelete" max-width="500px">
        <v-card>
          <v-card-title class="text-h5">{{
            $t("dialogo_borrar")
          }}</v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="closeDelete"
              >{{ $t('cancel') }}</v-btn
            >
            <v-btn color="blue darken-1" text @click="deleteItemConfirm"
              >OK</v-btn
            >
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>

    <v-alert type="success" v-if="showAlert">{{$t('travel-preferences-updated')}}</v-alert>
  </div>
</template>

<script>
import TouristService from "../services/TouristService";
import i18n from "../i18n.js";
import { LMap, LTileLayer, LGeoJson } from "vue2-leaflet";

import "leaflet/dist/leaflet.css";

import { Icon } from "leaflet";

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});

export default {
  name: "TravelsPreferencesComponent",
  components: {
    LMap,
    LTileLayer,
    LGeoJson
  },
  data() {
    return {
      touristService: new TouristService(),
      travelsPreferences: [],
      tourists: [],

      buttonAddModify: i18n.t("add"),
      cardTitle: i18n.t("add-travel-preferences"),

      valid: false,
      dialogDelete: false,
      hideForm: true,
      showAlert: false,

      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      zoom: 8,
      center: [28.4883552080302, -16.315565093647496],

      idTourist: "",
      idTravel: "",
      itemIndex: -1,
      path: this.$route.path,

      headers: [
        { text: "ID", value: "id", class: "white--text" },
        { text: i18n.t("tourist"), value: "tourist", class: "white--text" },
        { text: i18n.t("destination"), value: "destination", class: "white--text"},
        { text: i18n.t("budget"), value: "budget", class: "white--text" },
        { text: i18n.t("departureDatetime"), value: "departureDatetime", class: "white--text" },
        { text: i18n.t("returnDatetime"), value: "returnDatetime", class: "white--text" },
        { text: i18n.t("transport"), value: "transport", class: "white--text" },
        { text: i18n.t('actions'), value: 'actions', sortable: false , class: "white--text" },

      ],

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


      editedItem: {
        id: "",
        destinationGeolocation: [],
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

      defaultItem: {
        id: "",
        destinationGeolocation: [],
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
      }
    };
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      let travelPreferencesT = {};
      this.travelsPreferences.splice(0);
      this.touristService.getAll().then((data) => {
        this.tourists = data.data;

        for (let i = 0; i < this.tourists.length; i++) {
          if (this.tourists[i].travelsPreferences != null) {   // Voy almacenando los viajes de los turistas para mostralos en la tabla
            if (this.tourists[i].travelsPreferences.length > 0) {
              for (let j = 0; j < this.tourists[i].travelsPreferences.length; j++) {
                travelPreferencesT = Object.assign({}, this.tourists[i].travelsPreferences[j]);
                travelPreferencesT.tourist = this.tourists[i].id + " - " + this.tourists[i].name + " - (" + this.tourists[i].dni + ")";
                if (travelPreferencesT.transport == true) {
                  travelPreferencesT.transport = i18n.t("yes")
                } else {
                  travelPreferencesT.transport = i18n.t("no");
                }

                if (travelPreferencesT.departureDatetime != null) {
                  travelPreferencesT.departureDatetime = travelPreferencesT.departureDatetime.split("T").join(" ")
                } 

                if (travelPreferencesT.returnDatetime != null) {
                  travelPreferencesT.returnDatetime = travelPreferencesT.returnDatetime.split("T").join(" ")
                }
                this.travelsPreferences.push(travelPreferencesT);
              }
            }
          }
        }
      });

      this.editedItem = Object.assign({}, this.defaultItem);
      this.editedItem.activities = Object.assign({}, this.defaultItem.activities);
      this.idTourist = "";
      this.itemIndex = -1,
      this.hideForm = true;
    },

    loadLonLat(event) {
      this.editedItem.destinationGeolocation.coordinates.splice(0);
      this.editedItem.destinationGeolocation.coordinates.push(event.latlng.lng, event.latlng.lat);      
    },

    close() {
      this.hideForm = true;
      this.editedItem = Object.assign({}, this.defaultItem);
      this.idTourist = -1;
      
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.idTravel = "";
        this.idTourist = "";
      });
    },

    deleteItem(item) {
      this.idTravel = item.id;
      this.idTourist = item.tourist.split(" - ")[0];      
      this.dialogDelete = true;
    },

    deleteItemConfirm() {
      this.touristService.deleteTravelPreferences(this.idTourist, this.idTravel).then((data) => {
          if (data.status == 200) {
            this.initialize();
            this.closeDelete();
            this.deletedItem = this.defaultItem;
          } else {
            console.error("Ha habido un error en el borrado");
          }
        });
    },

    createTourismItinerary(item) {
      this.editedItem = Object.assign({}, item);
      
      this.$router.push({name: "tourism-itinerary", params: {idTourist: this.editedItem.tourist.split(" - ")[0], idTravelPreferences: this.editedItem.id}});
    },

    editItem(item) {
      this.itemIndex = this.travelsPreferences.indexOf(item);
      this.editedItem = Object.assign({}, item);
      
      if (this.editedItem.transport == i18n.t("yes")) {
        this.editedItem.transport = true;
      } else {
        this.editedItem.transport = false;
      }

      if (this.editedItem.departureDatetime != null) {
        this.editedItem.departureDatetime = this.editedItem.departureDatetime.split(" ").join("T")
      } 

      if (this.editedItem.returnDatetime != null) {
        this.editedItem.returnDatetime = this.editedItem.returnDatetime.split(" ").join("T")
      }

      let activitiesAux = Object.assign({}, this.defaultItem.activities)
      this.editedItem.activities.forEach((activitie) => {
        activitiesAux[activitie] = true;
      })

      this.editedItem.activities = Object.assign({}, activitiesAux);

      this.idTourist = this.editedItem.tourist.split(" - ")[0];
      this.hideForm = false;
    },

    modify() {
      let validacion = this.$refs.form.validate();

      if (validacion == true) {

        let travelPreferencesP = {
            id: this.editedItem.id,
            destinationGeolocation: this.editedItem.destinationGeolocation,
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

        this.touristService.modifyTravelPreferences(this.idTourist, travelPreferencesP.id-1, travelPreferencesP).then((data) => {
          if (data.status == 201) {
            this.showAlert = true;
            setTimeout(() => {this.showAlert = false;},2000);
            this.initialize();
          } else {
            console.error(data);
          }
        }).catch(err => {
          if (err.response.data.message) {
            alert(err.response.data.message)
          } else {
            alert(err.message);
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