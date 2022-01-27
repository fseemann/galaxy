import {createApp} from 'vue'
import App from './App.vue'
import {createPinia} from "pinia";
import PageLogin from "./pages/page-login.vue";
import PageGalaxyList from "./pages/page-galaxy-list.vue";
import {createRouter, createWebHashHistory} from "vue-router"

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {path: '/', component: PageLogin},
        {path: '/galaxies', component: PageGalaxyList}
    ]
})

const app = createApp(App);
app.use(router)
app.use(createPinia())
app.mount('#app')
