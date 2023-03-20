<template>
  <div class="locale-changer">
      <v-select class="select-switcher-language"
        v-model="$i18n.locale"
        :items="langs"
        menu-props="auto"
        label="Select"
        v-on:change="changeRoute"
        hide-details
        single-line
        color-text="white"
        solo
      ></v-select>
  </div>
</template>

<script>
import $i18n from '../i18n.js'

export default {
  name: "LanguageSwitcherComponent",
  data() {
      return {
          langs: $i18n.availableLocales
      }
  },
  methods: {
    changeRoute(idioma) {
        let ruta = (this.$route.path).substr(3, (this.$route.path).length)
        this.$router.push({path: "/"+idioma+ruta, replace: true})
        this.$router.go();
    }
  }
};
</script>

<style>
.select-switcher-language {
  color: white !important
}
</style>