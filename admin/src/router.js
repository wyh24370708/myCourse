import Vue from "vue"
import Router from "vue-router"
import Login from "./views/login.vue"
import Admin from "./views/admin.vue"
import Welcome from "./views/admin/welcome.vue"
import Chapter from "./views/admin/chapter.vue"
import Section from "./views/admin/section.vue"
import Course from "./views/admin/course.vue"
import Category from "./views/admin/category.vue"
import Teacher from "./views/admin/teacher.vue"
import Profile from "./views/admin/profile.vue"
import Course_content from "./views/admin/course_content.vue"
import User from "./views/admin/user.vue"

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
        path: "*",//访问任何路径都会跳转到/login路径
        redirect: "/login",
    },{
        path: "",//访问任何路径都会跳转到/login路径
        redirect: "/login",
    },{
        path: "/login",
        component: Login //如果访问/login路径会加载login组件,显示登陆页面的内容
    },{
        path: "/",
        name: "admin",
        component: Admin,
        children: [{//子路由的路径不要加 /
            path: "welcome",
            name: "welcome",
            //name属性值-->获取菜单id值
            //前面有过约定: id命名和路由相关      welcome   <-->  welcome-sidebar
            //这是一种规范: 约定由于配置(按照约定编程)
            component: Welcome
        },{
            path: "business/chapter",
            name: "business/chapter",
            component: Chapter
        },{
            path: "business/section",
            name: "business/section",
            component: Section
        },{
            path: "business/course",
            name: "business/course",
            component: Course
        },{
            path: "business/course_content",
            name: "business/course_content",
            component: Course_content
        },{
            path: "business/category",
            name: "business/category",
            component: Category
        },{
            path: "business/teacher",
            name: "business/teacher",
            component: Teacher
        },{
            path: "file/profile",
            name: "file/profile",
            component: Profile
        },{
            path: "system/user",
            name: "system/user",
            component: User
        }]

    }]
})