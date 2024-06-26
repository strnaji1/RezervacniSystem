import { createApp } from "vue"
import App from "./App.vue"
import router from "./router"
import store from "./store"
import BootstrapVue3 from "bootstrap-vue-3"

// Import Bootstrap and BootstrapVue3 CSS files
import "bootstrap/dist/css/bootstrap.css"
import "bootstrap-vue-3/dist/bootstrap-vue-3.css"

const app = createApp(App)

// Make BootstrapVue3 available throughout your project
app.use(BootstrapVue3)

app.use(router)
app.use(store)
app.mount("#app")
