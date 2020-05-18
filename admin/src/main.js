//本地为ES5,vue为ES6,报错转换一下
//main.js是vue的入口文件n,初始化vue实例,集成所需要的插件
import Vue from 'vue'
import App from './App.vue'
//引入router组件
import router from './router'
//引入axios
import Axios from "axios";//Alt+Enter加入字典中

Vue.config.productionTip = false
//理解为全局变量,在任意vue组件使用,this.$ajax获取值  $表示Vue全局属性的一个约定
Vue.prototype.$ajax = Axios;

//渲染id为app的样式
new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
