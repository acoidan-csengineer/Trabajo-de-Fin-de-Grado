import axios from 'axios'

export default class PointOfInterestService {
    url = "http://localhost:8080/pois/"

    getAll() {
        return axios.get(this.url)
    }

    getOne(id) {
        return axios.get(this.url+id)
    }

    deleteAll() {
        return axios.delete(this.url);
    }

    delete(id) {
        return axios.delete(this.url+id)
    }

    modify(id, item) {
        return axios.put(this.url+id, item);
    }

    add(item) {
        return axios.post(this.url, item)
    }

    addAll(itemOrItems) {
        if (Array.isArray(itemOrItems)) {
            for (let i = 0; i < itemOrItems.length; i++) {
                axios.post(this.url, itemOrItems[i])
            }

            return true;
        } else {
            axios.post(this.url, itemOrItems)

            return true;
        }
    }
}