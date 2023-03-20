<template>
  <div>
    <div id="pointsOfInterestForm">
      <v-card class="mb-5">
        <v-card-title>{{ $t('add-poi') }}</v-card-title>
        <v-card-text>
          <v-form v-model="valid" ref="form">
            <v-row>
              <v-col class="my-auto" cols="12" md="4">
                <v-text-field
                  v-model="editedItem.name"
                  :rules="rules.nameRules"
                  :counter="100"
                  :label="$t('nombre')"
                  required
                ></v-text-field>
              </v-col>

              <v-col class="my-auto" cols="12" md="4">
                <v-text-field
                  v-model="editedItem.town"
                  :rules="rules.townRules"
                  :label="$t('town')"
                  required
                ></v-text-field>
              </v-col>

              <v-col class="my-auto" cols="12" md="4">
                <v-text-field
                  v-model="editedItem.postalCode"
                  :rules="rules.postalCodeRules"
                  :counter="10"
                  :label="$t('postalCode')"
                  required
                ></v-text-field>
              </v-col>

              <v-col class="my-auto" cols="12" md="3">
                <v-text-field
                  v-model="editedItem.averageCost"
                  :label="$t('average-cost')"
                  type="number"
                  min="0"
                  step="0.5"
                  required
                ></v-text-field>
              </v-col>

              <v-col class="my-auto" cols="12" md="3">
                <v-text-field
                  v-model="editedItem.stayHourNumber"
                  :label="$t('stay-hour-number')"
                  min="0"
                  type = "number"
                  required
                ></v-text-field>
              </v-col>

              <v-col class="my-auto" cols="12" md="4">
                <v-text-field
                  v-model="editedItem.applications"
                  :label="$t('applications')"
                  required
                ></v-text-field>
              </v-col>
              
              <v-col cols="12">
                <div>
                  {{ $t("categories") }}
                </div>

                <v-row>
                  <v-col cols="auto" class="mx-auto">
                    <v-checkbox
                      v-model="editedItem.categories.SUNANDBEACH"
                      :label="$t('sun-and-beach')"
                    ></v-checkbox>
                  </v-col>

                  <v-col cols="auto" class="mx-auto">
                    <v-checkbox
                      v-model="editedItem.categories.RURAL"
                      :label="$t('rural')"
                    ></v-checkbox>
                  </v-col>

                  <v-col cols="auto" class="mx-auto">
                    <v-checkbox
                      v-model="editedItem.categories.NATURE"
                      :label="$t('nature')"
                    ></v-checkbox>
                  </v-col>

                  <v-col cols="auto" class="mx-auto">
                    <v-checkbox
                      v-model="editedItem.categories.GASTRONOMY"
                      :label="$t('gastronomy')"
                    ></v-checkbox>
                  </v-col>

                  <v-col cols="auto" class="mx-auto">
                    <v-checkbox
                      v-model="editedItem.categories.CULTURE"
                      :label="$t('culture')"
                    ></v-checkbox>
                  </v-col>
                </v-row>
              </v-col>
                  

              <v-col cols="12">
                <v-row>
                  <v-col class="my-auto" cols="12">
                    {{$t("hourly-and-opening-days")}}
                  </v-col>
                  <v-row class="mx-0">
                    <v-col cols="auto" class="mx-auto">
                      <v-checkbox
                        :label="$t('days.monday')"
                        v-model="editedItem.openingDays.MONDAY"
                      ></v-checkbox>
                    </v-col>
                    <v-col cols="auto" class="mx-auto">
                      <v-checkbox
                        :label="$t('days.tuesday')"
                        v-model="editedItem.openingDays.TUESDAY"
                      ></v-checkbox>
                    </v-col>
                    <v-col cols="auto" class="mx-auto">
                      <v-checkbox
                        :label="$t('days.wednesday')"
                        v-model="editedItem.openingDays.WEDNESDAY"
                      ></v-checkbox>
                    </v-col>
                    <v-col cols="auto" class="mx-auto">
                      <v-checkbox
                        :label="$t('days.thursday')"
                        v-model="editedItem.openingDays.THURSDAY"
                      ></v-checkbox>
                    </v-col>
                    <v-col cols="auto" class="mx-auto">
                      <v-checkbox
                        :label="$t('days.friday')"
                        v-model="editedItem.openingDays.FRIDAY"
                      ></v-checkbox>
                    </v-col>
                    <v-col cols="auto" class="mx-auto">
                      <v-checkbox
                        :label="$t('days.saturday')"
                        v-model="editedItem.openingDays.SATURDAY"
                      ></v-checkbox>
                    </v-col>
                    <v-col cols="auto" class="mx-auto">
                      <v-checkbox
                        :label="$t('days.sunday')"
                        v-model="editedItem.openingDays.SUNDAY"
                      ></v-checkbox>
                    </v-col>
                  </v-row>
                  
    
                  <v-col class="my-auto" cols="12" md="6">
                    <v-text-field
                      v-model="editedItem.openingTime"
                      :label="$t('openingTime')"
                      type="time"
                      required
                    ></v-text-field>
                  </v-col>

                  <v-col class="my-auto" cols="12" md="6">
                    <v-text-field
                      v-model="editedItem.closingTime"
                      :label="$t('closingTime')"
                      type="time"
                      required
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-col>


              <v-col class="my-auto" cols="12" md="12">
                <v-textarea
                  solo
                  v-model="editedItem.description"
                  :label="$t('description')"
                  required
                ></v-textarea>
              </v-col>



              <v-col cols="12">
                <v-row>
                  <v-col class="my-auto" cols="auto">
                    {{$t('click-coordinates')}}
                  </v-col>

                  <v-col class="my-auto" cols="auto">
                    <v-btn fab dark small color="primary" @click="addCoordinate">
                      <v-icon dark>
                        mdi-plus
                      </v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </v-col>


              <v-col cols="12" v-for="(coordinate, index) in this.editedItem.coordinates" :key="index">
                <v-row>
                  <v-col class="my-auto" cols="12" md="5">
                    <v-text-field
                      v-model.number="editedItem.coordinates[index][0]"
                      :rules="rules.coordinatesRules"
                      :label="$t('latitude')"
                      type="number"
                      step="0.01"
                      required
                    ></v-text-field>
                  </v-col>

                  <v-col class="my-auto" cols="12" md="5">
                    <v-text-field
                      v-model.number="editedItem.coordinates[index][1]"
                      :rules="rules.coordinatesRules"
                      :label="$t('longitude')"
                      type="number"
                      step="0.01"
                      required
                    ></v-text-field>
                  </v-col>
                  

                  <v-col class="my-auto" cols="auto" v-if="parseInt(index)>parseInt(0)">
                    <v-btn fab dark small color="red" @click="removeCoordinate">
                      <v-icon dark>
                        mdi-delete
                      </v-icon>
                    </v-btn>
                  </v-col>
                </v-row>
              </v-col>      
            </v-row>
          </v-form>
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="white" @click="add">
            {{ $t('add') }}
          </v-btn>
          <v-btn  color="red"  class="white--text" @click="back"> 
            {{ $t('cancel') }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </div>

    <div class="pointOfInterestMap">
      <l-map id="poi-map" ref="map" style="height: 500px" :zoom="zoom" :center="center" @click="loadLonLat">
        <l-tile-layer :url="url"></l-tile-layer>
        <l-marker v-for="marker, index in markers" :lat-lng="marker" :key="index"></l-marker>
      </l-map>
    </div>


    <v-alert type="success" v-if="showAlert">{{$t('poi-created')}}</v-alert>
  </div>
</template>

<script>
import PointOfInterestService from '../services/PointOfInterestService.js';
import { LMap, LTileLayer , LMarker} from "vue2-leaflet";
import i18n from '../i18n.js'

import "leaflet/dist/leaflet.css";

import { Icon } from "leaflet";

delete Icon.Default.prototype._getIconUrl;
Icon.Default.mergeOptions({
  iconRetinaUrl: require("leaflet/dist/images/marker-icon-2x.png"),
  iconUrl: require("leaflet/dist/images/marker-icon.png"),
  shadowUrl: require("leaflet/dist/images/marker-shadow.png"),
});

export default {
  components: {
    LMap,
    LTileLayer,
    LMarker
  },
  data() {
    return {
      pointOfInterestService: new PointOfInterestService(),

      valid: false,
      showAlert: false,
      
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      zoom: 8,
      center: [28.4883552080302, -16.315565093647496],
      markers: [],
      
      counterClicks: 0,

      rules: {
        nameRules: [
          (v) => !!v || i18n.t('points-of-interest-rules.nameRules1'),
          (v) => v.length <= 100 || i18n.t('points-of-interest-rules.nameRules2'),
        ],
        townRules: [
          (v) => !!v || i18n.t('points-of-interest-rules.townRules1'),
          (v) => /.*[¿?[\]{}()+_%@<>¬¡!#~]+.*/.test(v) == false || i18n.t('points-of-interest-rules.townRules2')
        ],
        postalCodeRules: [
          (v) => !!v || i18n.t('points-of-interest-rules.postalCodeRules1'),
          (v) => /^([\d]*)$/.test(v) || i18n.t('points-of-interest-rules.postalCodeRules2')
        ],
        coordinatesRules: [
          (v) => !!v || i18n.t('points-of-interest-rules.coordinatesRules'),
        ],
      },

      editedItem: {
        name: '',
        description: '',
        coordinates: [['', '']],
        town: '',
        postalCode: '',
        averageCost: 0,
        stayHourNumber: 0,
        applications: '',
        categories: {
          SUNANDBEACH: false,
          RURAL: false,
          GASTRONOMY: false,
          NATURE: false,
          CULTURE: false
        },
        openingDays: {
          MONDAY: false,
          TUESDAY: false,
          WEDNESDAY: false,
          THURSDAY: false,
          FRIDAY: false,
          SATURDAY: false,
          SUNDAY: false
        },
        openingTime: '',
        closingTime: ''
      },
      defaultItem: {
        name: '',
        description: '',
        coordinates: [['', '']],
        town: '',
        postalCode: '',
        averageCost: 0,
        stayHourNumber: 0,
        applications: '',
        categories: {
          SUNANDBEACH: false,
          RURAL: false,
          GASTRONOMY: false,
          NATURE: false,
          CULTURE: false
        },
        openingDays: {
          MONDAY: false,
          TUESDAY: false,
          WEDNESDAY: false,
          THURSDAY: false,
          FRIDAY: false,
          SATURDAY: false,
          SUNDAY: false
        },
        openingTime: '',
        closingTime: ''
      }
    };
  },

  methods: {
    loadLonLat(event) {
      if (this.counterClicks >= this.editedItem.coordinates.length ) {
        this.counterClicks = 0;
      }

      // Estas tres líneas las pongo porque si no no me carga la longitud y la latitud en el formulario cuando pulso en el mapa, sin embargo, si cambio algún atributo de editedItem o el objeto entero si me carga
      let auxItem = JSON.parse(JSON.stringify(this.editedItem));
      this.editedItem = JSON.parse(JSON.stringify(this.defaultItem));
      this.editedItem = JSON.parse(JSON.stringify(auxItem));

      this.editedItem.coordinates[this.counterClicks] = ["", ""]
      this.editedItem.coordinates[this.counterClicks][0] = event.latlng.lat;
      this.editedItem.coordinates[this.counterClicks][1] = event.latlng.lng;

      if (this.counterClicks == this.markers.length)
        this.markers.push([event.latlng.lat, event.latlng.lng]);
      else {
        this.markers.splice(this.counterClicks, 1, [event.latlng.lat, event.latlng.lng])
      }
      
      this.counterClicks++;
    },

    addCoordinate() {
      this.editedItem.coordinates.push(["", ""]);
    },

    removeCoordinate(index) {
      this.editedItem.coordinates.splice(index, 1);
      this.markers.splice(index, 1);
    },

    back() {
      let ruta = (this.$route.path).substr(0, (this.$route.path).length-4)
      this.$router.push({path: ruta})
      this.$router.go();
    },

    add() {
      let validacion = this.$refs.form.validate();
      if(validacion == true) {
        let requestJSON = JSON.parse(JSON.stringify({
          "name": this.editedItem.name,
          "town": this.editedItem.town,
          "description": this.editedItem.description,
          "postalCode": this.editedItem.postalCode,
          "averageCost": this.editedItem.averageCost,
          "stayHourNumber": this.editedItem.stayHourNumber,
          "categories": [],
          "applications": this.editedItem.applications,
          "openingDays": []
        }));
        
        for (let categorie in this.editedItem.categories) {
          if (this.editedItem.categories[categorie] == true) {
            requestJSON.categories.push(categorie)
          }
        }

        for (let day in this.editedItem.openingDays) {
          if (this.editedItem.openingDays[day] == true) {
            requestJSON.openingDays.push(day)
          }
        }

        if (this.editedItem.openingTime != "" && this.editedItem.closingTime != "" ) {
          requestJSON.hourly = this.editedItem.openingTime + " - " + this.editedItem.closingTime
        }
        
        if (this.editedItem.coordinates.length == 1) {
          requestJSON.geolocationPoint = {
            "type": "Point",
            "coordinates": [this.editedItem.coordinates[0][1], this.editedItem.coordinates[0][0]]
          }
        } else {
            if (this.editedItem.coordinates.length == 2) {
              requestJSON.geolocationLineString = JSON.parse(JSON.stringify({
                "type": "LineString",
                "coordinates": [
                  [this.editedItem.coordinates[0][1], this.editedItem.coordinates[0][0]],
                  [this.editedItem.coordinates[1][1], this.editedItem.coordinates[1][0]]
                ]
              }));
            } else {
                if (this.editedItem.coordinates.length > 2) {
                  requestJSON.geolocationPolygon = JSON.parse(JSON.stringify({
                    "type": "Polygon",
                    "coordinates": [[]]
                  }));

                  for (let i = 0; i < this.editedItem.coordinates.length; i++) {
                    requestJSON.geolocationPolygon.coordinates[0].push([this.editedItem.coordinates[i][1], this.editedItem.coordinates[i][0]])
                  }
                } else {
                  alert ("Ha habido un error con las coordenadas del punto de interés");
                }
            }
        }

        console.log(requestJSON)

        this.pointOfInterestService.add(requestJSON).then((data) => {
          if (data.status == 201) {
            this.showAlert = "true";
            setTimeout(() => {this.back()}, 2000);
          } else {
            console.error(data);
          }
        });
      }
    },
  }
};
</script>

<style>
.v-alert {
  bottom: 24px !important;
  right: 24px !important;
  position: fixed !important;
}
</style>