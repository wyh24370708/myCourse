import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Admin from './views/admin.vue'
import Welcome from './views/admin/welcome.vue'
import Chapter from './views/admin/chapter.vue'
import Section from './views/admin/section.vue'
import Course from './views/admin/course.vue'

/**
 * 1.router是VueRouter的一个对象，通过Vue.use(VueRouter)和VueRouter构造函数得到一个router的实例对象，
 *      这个对象中是一个全局的对象，他包含了所有的路由包含了许多关键的对象和属性。
 * 2.route是一个跳转的路由对象，每一个路由都会有一个route对象，是一个局部的对象，可以获取对应的name,path,params,query等
 */
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
            name: 'welcome',
            //name属性值-->获取菜单id值
            //前面有过约定: id命名和路由相关      welcome   <-->  welcome-sidebar
            //这是一种规范: 约定由于配置(按照约定编程)
            component: Welcome
        },{
            path: 'business/chapter',
            name: 'business/chapter',
            component: Chapter
        },{
            path: 'business/section',
            name: 'business/section',
            component: Section
        },{
            path: 'business/course',
            name: 'business/course',
            component: Course
        }]

    }]
})