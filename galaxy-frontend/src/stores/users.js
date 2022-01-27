import {defineStore} from "pinia";

export default defineStore('users', {
    state: () => ({
        authenticatedId: null,
        entitiesById: {},
        idsByParams: {}
    }),
    getters: {
        getSelf(state) {
            return state.entitiesById[state.authenticatedId]
        }
    },
    actions: {
        async login(payload) {
            const response = await fetch('/api/users/login', {
                method: 'POST',
                body: new URLSearchParams([['username', payload.username], ['password', payload.password]])
            })
            const user = response.json()

            this.authenticatedId = user.id
            Object.assign(this.entitiesById, {[user.id]: user})
            return user
        },
        async logout() {
            await fetch('/api/users/logout', {
                method: 'POST'
            })
            this.$reset()
        }
    }
})