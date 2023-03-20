<template>
  <div>
    <div id="addTouristForm">
      <v-card class="mt-5 br-10px">
        <v-card-title>{{$t('add-tourist')}}</v-card-title>
        <v-card-text>
          <v-form v-model="valid" ref="form">
            <v-row>
              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="editedItem.name"
                  :rules="rules.nameRules"
                  :counter="40"
                  :label="$t('nombre')"
                  required
                ></v-text-field>
              </v-col>

              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="editedItem.surname"
                  :rules="rules.surnameRules"
                  :counter="100"
                  :label="$t('surname')"
                  required
                ></v-text-field>
              </v-col>

              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="editedItem.email"
                  :rules="rules.emailRules"
                  :label="$t('email')"
                  required
                ></v-text-field>
              </v-col>

              <v-col
                cols="12"
                md="4"
              >
                <v-text-field
                  v-model="editedItem.dni"
                  :rules="rules.dniRules"
                  :counter="15"
                  label="DNI, NIE o pasaporte"
                  required
                ></v-text-field>
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
            {{$t('add')}}
          </v-btn>
        </v-card-actions>   
      </v-card>
       
    </div>


    <v-alert type="success" v-if="showAlert">{{$t('tourist-created')}}</v-alert>
  </div>
  
</template>

<script>
import TouristService from '../services/TouristService'
import i18n from "../i18n.js";

export default {
  name: "AddTouristComponent",
  data() {
      return {
        touristService: new TouristService(),

        valid: false,
        showAlert: false,

        rules: {
          nameRules: [
            v => !!v || i18n.t('tourists-rules.nameRules1'),
            v => v.length <= 100 || i18n.t('tourists-rules.nameRules2'),
            v => /.*[\d¿?[\]{}()+_%@<>¬¡!#~]+.*/.test(v) == false || i18n.t('tourists-rules.surnameRules3')
          ],
          surnameRules: [
            v => !!v || i18n.t('tourists-rules.surnameRules1'),
            v => v.length <= 100 || i18n.t('tourists-rules.surnameRules2'),
            v => /.*[\d¿?[\]{}()+_%@<>¬¡!#~]+.*/.test(v) == false || i18n.t('tourists-rules.surnameRules3')
          ],
          emailRules: [
            v => !!v || i18n.t('tourists-rules.emailRules1'),
            v => /^.*@.*(\.).*$/.test(v) || i18n.t('tourists-rules.emailRules2'),
            v => /^[A-Za-z0-9].*/.test(v) || i18n.t('tourists-rules.emailRules3'),
            v => /^[A-Za-z0-9].*\.{2,}.*@.*/.test(v) == false || i18n.t('tourists-rules.emailRules4'),
            v => /^[A-Za-z0-9](\.{0,1}[\w-])*@.*/.test(v) || i18n.t('tourists-rules.emailRules5'),
            v => /^[A-Za-z0-9](\.{0,1}[\w-]){5,29}@.*/.test(v) || i18n.t('tourists-rules.emailRules6'),
            v => /^[A-Za-z0-9](\.{0,1}[\w-]){5,29}@[A-Za-z0-9].*/.test(v) || i18n.t('tourists-rules.emailRules7'),
            v => /^[A-Za-z0-9](\.{0,1}[\w-]){5,29}@[A-Za-z0-9].*\.{2,}.*/.test(v) == false || i18n.t('tourists-rules.emailRules8'),
            v => /^[A-Za-z0-9](\.{0,1}[\w-]){5,29}@[A-Za-z0-9](\.{0,1}[A-Za-z0-9])+\.\w+$/.test(v) || i18n.t('tourists-rules.emailRules9'),
            v => /^[A-Za-z0-9](\.{0,1}[\w-]){5,29}@[A-Za-z0-9](\.{0,1}[A-Za-z0-9]){1,19}\.\w+$/.test(v) || i18n.t('tourists-rules.emailRules10'),
            v => /^[A-Za-z0-9](\.{0,1}[\w-]){5,29}@[A-Za-z0-9](\.{0,1}[A-Za-z0-9]){1,19}\.([a-zA-Z])+$/.test(v) || i18n.t('tourists-rules.emailRules11'),
            v => /^[A-Za-z0-9](\.{0,1}[\w-]){5,29}@[A-Za-z0-9](\.{0,1}[A-Za-z0-9]){1,19}(\.[A-Za-z]{2,10})$/.test(v) || i18n.t('tourists-rules.emailRules12'),
          ],
          dniRules: [
            v => !!v || 'El DNI, NIE o pasaporte es obligatorio'
          ]
        }
,


        editedItem: {
          name: '',
          surname: '',
          email: '',
          dni: '',
          travelsPreferences: []
        },
      }
  },

  methods: {
    back () {
        let ruta = (this.$route.path).substr(0, (this.$route.path).length-4)
        this.$router.push({path: ruta})
        this.$router.go();
    },

    add () {
      let validacion = this.$refs.form.validate();
      if(validacion == true) {
        this.touristService.add(this.editedItem).then(data => {
          if (data.status == 201){
            this.showAlert = "true";
            setTimeout(() => {this.back()}, 2000);
          } else {
            console.error(data)
          }
        });
      }
    }
  }
};
</script>

<style>

</style>