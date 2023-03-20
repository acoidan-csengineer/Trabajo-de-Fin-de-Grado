<template>
  <div>
    <v-btn class="mb-5" color="white" :href="path + '/add'">
      <v-icon
        left
        medium
      >
        mdi-plus
      </v-icon>
      {{$t('add-tourist')}}
    </v-btn>

    <div id="touristModifyForm">
      <v-card class="mb-5 br-10px" v-if="!hideForm">
        <v-card-title>{{$t('modify-tourist')}}</v-card-title>
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
                   :label="$t('dni')"
                  required
                ></v-text-field>
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
            {{$t('modify')}}
          </v-btn>
        </v-card-actions>   
      </v-card>
       
    </div>
    
    <div id="travelsPreferencesTable">
      <v-data-table :headers="headers" :items="tourists" :items-per-page="10" sort-by="id" class="elevation-2">

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
          <v-card-title class="text-h5">{{$t("dialogo_borrar")}}</v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="closeDelete">{{ $t('cancel') }}</v-btn>
            <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>
      
    </div>

    <v-alert type="success" v-if="showAlert">{{$t('tourist-updated')}}</v-alert>
  </div>
  
</template>

<script>
import TouristService from '../services/TouristService'
import i18n from '../i18n.js'
export default {
  name: "TouristsComponent",
  data() {
      return {
        touristService: new TouristService(),
        tourists: [],
        valid: false,
        dialogDelete: false,
        path: this.$route.path,

        hideForm: true,
        showAlert: false,

        // editItemOption: 0,
        itemIndex: -1,

        headers: [
          {text: 'ID', value:'id', class: "white--text"},
          {text: i18n.t('nombre'), value:'name', class: "white--text"},
          {text: i18n.t('surname'), value:'surname', class: "white--text"},
          {text: i18n.t('email'), value:'email', class: "white--text"},
          {text: i18n.t('dni'), value:'dni', class: "white--text"},
          {text: i18n.t('actions'), value: 'actions', sortable: false , class: "white--text"},
        ],

        rules: {
          nameRules: [
            v => !!v || i18n.t('tourists-rules.nameRules1'),
            v => v.length <= 100 || i18n.t('tourists-rules.nameRules2'),
            v => /.*[\d¿?[\]{}()+_%@<>¬¡!#~]+.*/.test(v) == false || i18n.t('tourists-rules.surnameRules3')
          ],
          surnameRules: [
            v => !!v || i18n.t('tourists-rules.surnameRules1'),
            v => v.length <= 100 || i18n.t('tourists-rules.surnameRules2'),
            v => /.*[\d¿?[\]{}()+_%@<>¬¡!#~]+.*]+$/.test(v) == false || i18n.t('tourists-rules.surnameRules3')
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
        },

        editedItem: {
          name: '',
          surname: '',
          email: '',
          dni: '',
          travelsPreferences: []
        },
        defaultItem: {
          name: '',
          surname: '',
          email: '',
          dni: '',
          travelsPreferences: []
        },
      }
  },

  created() {
    this.initialize();
  },

  methods: {
    initialize() {
      this.touristService.getAll().then(data => {
        this.tourists = data.data;
      })

      this.editedItem = Object.assign({}, this.defaultItem);
      this.hideForm = true;
    },
    
    close () {
      this.hideForm = true
      this.editedItem = Object.assign({}, this.defaultItem)
      this.hideButtonCancel = true;
    },

    closeDelete () {
      this.dialogDelete = false
    },

    deleteItem (item) {
      this.itemIndex = this.tourists.indexOf(item)
      this.dialogDelete = true
    },

    deleteItemConfirm () {
      this.touristService.deleteOne(this.tourists[this.itemIndex].id).then(data => {
        if (data.status == 200){
          this.initialize();
          this.closeDelete();
          this.itemIndex = -1;
        } else {
          console.error("Ha habido un error en el borrado")
        }

      })
    },

    editItem (item) {
      this.itemIndex = this.tourists.indexOf(item);
      this.editedItem = Object.assign({}, item)
      this.hideForm = false;
      this.buttonAddModify = i18n.t("modify");
      this.cardTitle = i18n.t("modify-tourist");
      this.editItemOption = 1;
      this.hideButtonCancel = false;
    },

    modify () {
      let validacion = this.$refs.form.validate();
      if(validacion == true) {
        delete this.editedItem.id;
        this.touristService.modify(this.tourists[this.itemIndex].id, this.editedItem).then(data => {
          if (data.status == 201){
            this.showAlert = true;
            setTimeout(() => {this.showAlert = false;},2000);
            this.initialize();
          } else {
            console.error(data)
          }
        });
      }
    }
  }
};
</script>

<style> /* Con scope el estilo solo afecta a este componente */
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