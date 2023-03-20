<template>
  <v-app id="app">
    <toolbar-component></toolbar-component>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
import ToolbarComponent from '@/components/Toolbar.vue'

export default {
  name: 'App',
  components: {
    ToolbarComponent
  },
  data: () => ({
    //
  }),
};
</script>

<style>
main {
  background-color: #e4ded8;
}

.br-10px {
  border-radius: 10px !important;
}
</style>