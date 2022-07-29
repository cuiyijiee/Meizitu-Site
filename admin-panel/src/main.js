import {createApp} from 'vue'
import App from './App.vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import * as ElementPlusIcon from "@element-plus/icons-vue";

import router from "@/router"

const app = createApp(App)

app.use(router)
    .use(ElementPlus)
    .mount('#app')

for (const [key, component] of Object.entries(ElementPlusIcon)) {
    app.component(key, component)
}
