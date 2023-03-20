import axios from 'axios'

export default class TouristService {
    url = "http://localhost:8080/tourists/"

    getAll() {
        return axios.get(this.url)
    }

    getOne(id) {
        return axios.get(this.url+id)
    }

    deleteOne(id) {
        return axios.delete(this.url+id)
    }

    modify(id, item) {
        return axios.put(this.url+id, item)
    }

    add(item) {
        return axios.post(this.url, item)
    }

    addTravelPreferences(id, travelPreferences) {
        return axios.post(this.url+id+"/travels-preferences", travelPreferences)
    }

    modifyTravelPreferences(id, idT, travelPreferences) {
        return axios.put(this.url+id+"/travels-preferences/"+idT, travelPreferences)
    }

    deleteTravelPreferences(id, idT) {
        return axios.delete(this.url+id+"/travels-preferences/"+idT)
    }
}