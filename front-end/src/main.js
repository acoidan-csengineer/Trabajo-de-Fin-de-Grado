import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify'
import i18n from './i18n'

Vue.config.productionTip = false

// Se usa beforeEach route guard para cambiar el lenguaje
router.beforeEach((to, _from, next) => {

  // Usa el lenguaje desde el routing param o el lenguaje por defecto
  let language = to.params.lang;
  if (!language) {
    language = 'en'
  }

  // Cambia el lenguaje actual para i18n
  i18n.locale = language
  next()
})

new Vue({
  router,
  vuetify,
  i18n,
  render: h => h(App)
}).$mount('#app')
