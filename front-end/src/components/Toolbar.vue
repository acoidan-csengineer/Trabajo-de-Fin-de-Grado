<template>
  <v-app-bar class="toolbar" app>
    <img src="../assets/logoRIT2.svg" height="50px" width="auto">
    
    <v-spacer></v-spacer>

    <a :href="'/' + language + '/travels-preferences'">
      <v-icon
          class="mx-2"
          dark
          left
          large
          :href="'/' + language + '/travels-preferences'"
        >
          mdi-airplane
        </v-icon>
      </a>

    <a :href="'/' + language + '/tourists'">
      <v-icon
        class="mx-2"
        dark
        left
        large
      >
        mdi-account-supervisor
      </v-icon>
    </a>

    <a :href="'/' + language + '/pois'">
      <v-icon
        class="mx-2"
        dark
        left
        large
      >
        mdi-map-marker-path
      </v-icon>
    </a>

    <language-switcher-component/>
  </v-app-bar>
</template>

<script>
import $i18n from '../i18n.js'
import LanguageSwitcherComponent from './LanguageSwitcher.vue'

export default {
  name: "ToolbarComponent",
  data() {
      return {
        language: $i18n.locale
      }
  },
  created: function () {
    console.log($i18n.locale)
  },
  components: {
    LanguageSwitcherComponent
  },

};
</script>

<style scope>
  .toolbar {
    background-color: #5396AD !important;
    color: white !important;
  }

  a {
    text-decoration: none !important;
  }

  a :hover {
    background-color: rgba(0, 0, 0, 0.5);
  }
</style>