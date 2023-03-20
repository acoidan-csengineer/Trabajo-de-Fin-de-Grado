import axios from 'axios'

export default class TourismItinerarysService {
    url = "http://localhost:8080/recommendation-system/"

    getTourismItinerary(idT, idTP) {
        return axios.get(this.url+idT+"/"+idTP)
    }
}