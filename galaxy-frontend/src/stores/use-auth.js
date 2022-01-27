import {defineStore} from "pinia";

export default defineStore('auth', {
    state: () => ({
        user: null,
    }),
    actions: {
        async login(payload) {
            const response = await fetch('/api/users/login', {
                method: 'POST',
                body: new URLSearchParams([['username', payload.username], ['password', payload.password]])
            })
            const user = response.json()
            this.user = user
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