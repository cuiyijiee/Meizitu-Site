import {createRouter, createWebHashHistory} from "vue-router";


import Login from "@/view/Login";

import Main from "@/view/Main/Main";
import CategoryManage from "@/view/Main/CategoryManage";
import PostManage from "@/view/Main/PostManage";


const routes = [
    {
        path: "/", component: Main,
        children: [{
            path: "home", component: CategoryManage
        }, {
            path: "about", component: PostManage
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
