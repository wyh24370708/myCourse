import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [{
        path: '*',//访问任何路径都会跳转到/login路径
        redirect: "/login",
    },{
        path: '/login',
        component: Login //如果访问/login路径会加载login组件,显示登陆页面的内容
    }]
})