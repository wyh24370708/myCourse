import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Admin from './views/admin.vue'
import Welcome from './views/admin/welcome.vue'
import Chapter from './views/admin/chapter.vue'

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
    },{
        path: '/admin',
        component: Admin,
        children: [{//子路由的路径不要加 /
            path: 'welcome',
            component: Welcome
        },{
            path: 'business/chapter',
            component: Chapter
        }]

    }]
})