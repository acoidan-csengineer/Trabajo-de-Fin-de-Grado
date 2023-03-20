<template>
  <div>
    <v-row>
      <v-col cols=12 md="auto">
        <v-btn class="mb-5" color="white" :href="path + '/add'">
          <v-icon
            left
            medium
          >
            mdi-plus
          </v-icon>
          {{$t('add-poi')}}
        </v-btn>
      </v-col>

      <v-col cols=12 md="auto">
        <v-btn class="mb-5" dark color="red" @click="dialogDeleteAll = true">
          <v-icon
            left
            medium
            dark
          >
            mdi-delete
          </v-icon>
          {{$t('delete-all-pois')}}
        </v-btn>
      </v-col>

      <v-col cols=12 md="auto">
        <v-btn class="mb-5" color="white" @click="dialogMassiveInsertion = true">
          <v-icon
            left
            medium
          >
            mdi-plus
          </v-icon>
          {{$t('massive-insertion-pois')}}
        </v-btn>
      </v-col>
    </v-row>
    

    <div id="pointsOfInterestForm">
      <v-card class="mb-5" v-if="!hideForm">
        <v-card-title>{{ $t('modify-poi') }}</v-card-title>
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
          <v-btn  color="red" class="white--text" @click="dialogDelete = true"> 
            Eliminar registro
          </v-btn>
          <v-spacer></v-spacer>
          <v-btn color="white" @click="save">
            {{ $t('modify') }}
          </v-btn>
          <v-btn  color="red"  class="white--text" @click="close"> 
            {{ $t('cancel') }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </div>

    <div class="pointOfInterestMap">
      <l-map id="poi-map" ref="map" style="height: 500px; position: sticky !important;" :zoom="zoom" :center="center" @click="loadLonLat">
        <l-tile-layer :url="url"></l-tile-layer>
        <l-geo-json :geojson="geojson" :options="options" @click="editItemFromMap" v-if="!editing"></l-geo-json>
        <l-marker v-for="marker, index in markers" :lat-lng="marker" :key="index"></l-marker>
      </l-map>
    </div>

    <div id="pointOfInteresTable">
      <v-data-table :headers="headers" :items="pois" :items-per-page="10" sort-by="id" class="elevation-10 mt-5">
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon
            small
            class="mr-2"
            @click="editItem(item)"
          >
            mdi-pencil
          </v-icon>
          <v-icon
            small
            @click="deleteItem(item)"
          >
            mdi-delete
          </v-icon>
        </template>
        <template v-slot:no-data>
          <v-btn
            color="primary"
            @click="initialize"
          >
            Reset
          </v-btn>
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

      <v-dialog v-model="dialogDeleteAll" max-width="500px">
        <v-card>
          <v-card-title class="text-h5">{{
            $t("dialog-delete-all")
          }}</v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialogDeleteAll = false"
              >{{ $t('cancel') }}</v-btn
            >
            <v-btn color="blue darken-1" text @click="deleteItemConfirm"
              >OK</v-btn
            >
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="dialogMassiveInsertion" max-width="500px">
        <v-card>
          <v-card-title class="text-h5">{{
            $t("select-file")
          }}</v-card-title>
          <v-card-text>
            <v-form ref="formDialog">
              <v-file-input
                v-model="file"
                :rules="rules.fileRules"
                truncate-length="15"
              ></v-file-input>
            </v-form>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialogMassiveInsertion = false">
              {{ $t('cancel') }}
            </v-btn>
            <v-btn color="blue darken-1" text @click="massiveInsertion()">
              {{ $t('continue') }}
            </v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>


    <v-alert type="success" v-if="showAlertUpdate">{{$t('poi-updated')}}</v-alert>
    <v-alert type="success" v-if="showAlertAdd">{{$t('pois-inserted')}}</v-alert>
  </div>
</template>

<script>
import PointOfInterestService from '../services/PointOfInterestService.js';
import i18n from "../i18n.js";
import { LMap, LTileLayer, LGeoJson, LMarker } from "vue2-leaflet";

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
    LGeoJson,
    LMarker
  },
  data() {
    return {
      pointOfInterestService: new PointOfInterestService(),

      valid: false,
      dialogDelete: false,
      dialogDeleteAll: false,
      hideForm: false,
      showAlertUpdate: false,
      showAlertAdd: false,
      clickOnGeojsonObject: false,
      editing: false,
      dialogMassiveInsertion: false,

      pois: [],
      
      url: "https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png",
      zoom: 8,
      center: [28.4883552080302, -16.315565093647496],
      geojson: {
        type: "FeatureCollection",
        features: [],
      },
      markers: [],
      
      itemIndex: -1,
      counterClicks: 0,
      path: this.$route.path,
      file: null,

      headers: [
          {text: 'ID', value:'id', class:'white--text'},
          {text: i18n.t('nombre'), value:'name', class:'white--text'},
          {text: i18n.t('descripcion'), value:'description', class: 'white--text'},
          {text: i18n.t('horario'), value:'hourly', class: 'white--text'},
          {text: i18n.t('town'), value:'town', class: 'white--text'},
          {text: i18n.t('postalCode'), value:'postalCode', class: 'white--text'},
          {text: i18n.t('categories'), value:'categories', class: 'white--text'},
          {text: i18n.t('applications'), value:'applications', class: 'white--text'},
          {text: i18n.t('actions'), value: 'actions', sortable: false, class: 'white--text'},
        ],

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
        fileRules: [
          (v) => !!v || i18n.t('points-of-interest-rules.fileRules')
        ]
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
      this.pois.splice(0);
      this.markers.splice(0);
      this.geojson.features.splice(0);
      this.editedItem = JSON.parse(JSON.stringify(this.defaultItem));
      this.hideForm = true;
      this.editing = false;

      this.pointOfInterestService.getAll().then((data) => {
        this.pois = JSON.parse(JSON.stringify(data.data));
        data.data.forEach(poi => {
          let jsonP = JSON.parse(JSON.stringify({
            "type": "Feature",
            "properties": {
              "id": poi.id,
              "name": poi.name,
              "description": poi.description,
              "town": poi.town,
              "postalCode": poi.postalCode,
              "categories": poi.categories,
              "applications": poi.applications,
              "hourly": poi.hourly
            },
            "geometry": {
              "type": "",
              "coordinates": []
            }
          }));

          if (poi.geolocationPoint != null) {
            jsonP.geometry = {
              "type": poi.geolocationPoint.type,
              "coordinates": poi.geolocationPoint.coordinates
            }
          } else {
            if (poi.geolocationLineString != null) {
              jsonP.geometry = {
                "type": poi.geolocationLineString.type,
                "coordinates": []
              }

              for(let i = 0; i < poi.geolocationLineString.coordinates.length; i++) {
                jsonP.geometry.coordinates.push(poi.geolocationLineString.coordinates[i].coordinates)
              }
            } else {
              if (poi.geolocationPolygon != null) {
                jsonP.geometry = {
                  "type": poi.geolocationPolygon.type,
                  "coordinates": [[]]
                }

                for(let i = 0; i < poi.geolocationPolygon.coordinates[0].coordinates.length; i++) {
                  jsonP.geometry.coordinates[0].push(poi.geolocationPolygon.coordinates[0].coordinates[i].coordinates)
                }
              }

              
            }
          }

          this.geojson.features.push(JSON.parse(JSON.stringify(jsonP)));
        });
      })
    },
    
    loadLonLat(event) {
      if (this.editing) {
        if (this.counterClicks >= this.editedItem.coordinates.length) {
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

      }

    },

    addCoordinate() {
      this.editedItem.coordinates.push(["", ""]);
    },

    removeCoordinate(index) {
      this.editedItem.coordinates.splice(index, 1);
      this.markers.splice(index, 1);
    },

    close() {
      this.editedItem = JSON.parse(JSON.stringify(this.defaultItem));
      this.hideForm = true;
      this.editing = false;
      this.markers.splice(0);
    },

    closeDelete() {
      this.dialogDelete = false;
    },

    editItemFromMap(event) {
      this.editedItem.editItem = Object.assign({}, this.defaultItem);
      let item = this.pois.filter(function(poi) { return poi.id === event.layer.feature.properties.id})
      this.counterClicks = 0;
      this.clickOnGeojsonObject = true;
      if (item.length > 0)
        this.editItem(item[0])
    },

    editItem (item) {
      this.itemIndex = this.pois.indexOf(item);

      this.editedItem.name = item.name;
      this.editedItem.description = item.description;
      this.editedItem.town = item.town;
      this.editedItem.postalCode = item.postalCode;
      this.editedItem.averageCost = item.averageCost;
      this.editedItem.stayHourNumber = item.stayHourNumber;
      this.editedItem.categories = item.categories;
      this.editedItem.applications = item.applications;

      let categoriesAux = Object.assign({}, this.defaultItem.categories)
      item.categories.forEach((categorie) => {
        categoriesAux[categorie] = true;
      })

      this.editedItem.categories = Object.assign({}, categoriesAux);

      let openingDaysAux = Object.assign({}, this.defaultItem.openingDays)
      item.openingDays.forEach((day) => {
        openingDaysAux[day] = true;
      })

      this.editedItem.openingDays = Object.assign({}, openingDaysAux);

      if (item.hourly != null) {
        this.editedItem.openingTime = item.hourly.substring(0, item.hourly.indexOf("-")-1);
        this.editedItem.closingTime = item.hourly.substring(item.hourly.indexOf("-")+2, item.hourly.length);
        console.log(this.editedItem.closingTime)
      }

      this.editedItem.coordinates.splice(0);

      if (item.geolocationPoint != null) {
        this.editedItem.coordinates.push([item.geolocationPoint.coordinates[1], item.geolocationPoint.coordinates[0]])
      } else {
        if (item.geolocationLineString != null) {
          
          item.geolocationLineString.coordinates.forEach((coordinate) => {
            this.editedItem.coordinates.push([coordinate.coordinates[1], coordinate.coordinates[0]]);
          });
        } else {
          if (item.geolocationPolygon != null) {
            item.geolocationPolygon.coordinates[0].coordinates.forEach((coordinate) => {
              this.editedItem.coordinates.push([coordinate.coordinates[1], coordinate.coordinates[0]]);
            });
          }
        }
      }

      this.hideForm = false;
      this.editing = true;
    },

    deleteItem(item) {
      this.itemIndex = this.pois.indexOf(item);   
      this.dialogDelete = true
    },

    deleteItemConfirm() {
      if (this.dialogDelete) {
        this.pointOfInterestService.delete(this.pois[this.itemIndex].id).then((data) => {
          if (data.status == 200) {
            this.initialize();
            this.closeDelete();
            this.itemIndex = -1;
          } else {
            console.error("Ha habido un error en el borrado");
          }
        });
      } else {
        if (this.dialogDeleteAll) {
          this.pointOfInterestService.deleteAll().then((data) => {
            if (data.status == 200) {
              this.$router.go();
            } else {
              console.error("Ha habido un error en el borrado");
            }
          });
        }
      }
    },

    massiveInsertion() {
      let validacion = this.$refs.formDialog.validate();

      if (validacion) {
        let fileread = new FileReader();

        fileread.onload = (e) => {
          let content = e.target.result;
          let intern = JSON.parse(content); // parse json 

          let added = this.pointOfInterestService.addAll(intern);
          if (added) {
            this.initialize();
            this.showAlertAdd = true;
            setTimeout(() => {this.showAlertAdd = false;},4000);
            this.file = null;
            this.dialogMassiveInsertion = false;
          }
        };

        fileread.readAsText(this.file);
      }


    },

    save() {
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

        if (this.editedItem.openingTime != "" && this.editItem.closingTime != "" ) {
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

        this.pointOfInterestService.modify(this.pois[this.itemIndex].id, requestJSON).then(data => {
          if (data.status == 201){
            this.showAlertUpdate = true;
            setTimeout(() => {this.showAlertUpdate = false;},2000);
            this.initialize();
          } else {
            console.error(data)
          }
        });
      }
    },
  }
};
</script>

<style>
  #poi-map {
    position: sticky !important;
  }

  .v-data-table__wrapper {
    border-top-left-radius: 10px !important;
    border-top-right-radius: 10px !important;
  }

  .v-data-table-header {
    background-color: #5396AD !important;
  }

  .v-data-table {
    border-radius: 10px !important;
  }
</style>