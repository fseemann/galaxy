import {defineStore} from "pinia";

export default defineStore('galaxies', {
    state: () => ({
        byId: {},
        idsByParams: {}
    }),
    getters: {
        getGalaxies: (state) => (params) => (state.idsByParams[JSON.stringify(params)] || []).map(it => state.byId[it])
    },
    actions: {
        async fetchGalaxies(params) {
            const response = await fetch('/api/galaxies')
            const galaxies = await response.json()

            galaxies.reduce((acc, it) => {
                acc[it.id] = it
                return acc
            }, this.byId)
            Object.assign(this.idsByParams, {[JSON.stringify(params)]: galaxies.map(it => it.id)})

            return galaxies
        }
    }
})