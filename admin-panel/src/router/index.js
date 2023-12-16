import {createRouter, createWebHashHistory} from "vue-router";


import Login from "@/views/Login.vue";

import Main from "@/views/Main/Main.vue";
import CategoryManage from "@/views/Main/CategoryManage.vue";
import PostManage from "@/views/Main/PostManage.vue";


const routes = [
    {
        path: "/", component: Main,
        children: [{
            path: "", component: CategoryManage
        }, {
            path: "post", component: PostManage
        }]
    },
    {
        path: "/login", component: Login
    }
]

const routerConfig = createRouter({
    history: createWebHashHistory(),
    routes
})

export default routerConfig;
