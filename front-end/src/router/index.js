import i18n from '@/i18n'
import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Home.vue'
import PointsOfInterestView from '../views/PointsOfInterest.vue'
import AddPointOfInterestView from '../views/AddPointOfInterest.vue'
import TouristsView from '../views/Tourists.vue'
import AddTouristView from '../views/AddTourist.vue'
import TravelsPreferencesView from '../views/TravelsPreferences.vue'
import AddTravelPreferencesView from '../views/AddTravelPreferences.vue'
import TourismItineraryView from '../views/TourismItinerary.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: `/${i18n.locale}`
  },
  {
    path: '/:lang',
    component: {
      render(c) { return c('router-view') }
    },
    children: [
      {
        path: '/',
        name: 'home',
        component: HomeView
      },
      {
        path: 'pois',
        name: 'pois',
        component: PointsOfInterestView
      },
      {
        path: 'pois/add',
        name: 'add-poi',
        component: AddPointOfInterestView
      },
      {
        path: 'tourists',
        name: 'tourists',
        component: TouristsView,
      },
      {
        path: 'tourists/add',
        name: 'add-tourist',
        component: AddTouristView
      },
      {
        path: 'travels-preferences',
        name: 'travels-preferences',
        component: TravelsPreferencesView,
      },
      {
        path: 'travels-preferences/add',
        name: 'add-travel-preferences',
        component: AddTravelPreferencesView
      },
      {
        path: 'tourism-itinerary',
        name: 'tourism-itinerary',
        component: TourismItineraryView,
        props: true
      },
    ]
  }
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
