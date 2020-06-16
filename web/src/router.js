import Vue from "vue"
import Router from "vue-router"
import Index from "./views/index.vue"
import List from "./views/list.vue"

/**
 * 1.router是VueRouter的一个对象，通过Vue.use(VueRouter)和VueRouter构造函数得到一个router的实例对象，
 *      这个对象中是一个全局的对象，他包含了所有的路由包含了许多关键的对象和属性。
 * 2.route是一个跳转的路由对象，每一个路由都会有一个route对象，是一个局部的对象，可以获取对应的name,path,params,query等
 *
 * $router是整个路由对象，可以通过这个变量来跳转路由。$route是某一个路由，可以通过这个变量或取路由的名称、路径、参数等
 */
Vue.use(Router);

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/index",
    },{
        path: "/index",
        component: Index
    },{
        path: "/list",
        component: List
    }]

})