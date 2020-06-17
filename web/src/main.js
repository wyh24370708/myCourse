import Vue from 'vue'
import App from './app.vue'
import router from './router'
//引入axios
import Axios from "axios";
import filter from "./filter/filter.js";
//Alt+Enter加入字典中

Vue.config.productionTip = false;
//发送请求的全局变量
Vue.prototype.$ajax = Axios;

/**
 * 定义全局的过滤器
 * vue中的过滤器分为两种：(使用方式相同,作用域不同)
 *    1. 局部过滤器 (用在当前的vue实例中)
 *    2. 全局过滤器 (vue-cli项目中,所有vue页面都可用)
 *    当有局部和全局两个名称相同的过滤器时候，会以就近原则进行调用，即：局部过滤器优先于全局过滤器被调用！
 *    一个表达式可以使用多个过滤器。过滤器之间需要用管道符“|”隔开。其执行顺序从左往右
 *          例如:{{ msg | filter1 | filter2 | filter3 }}
 *
 *     //局部:案例
 *     创建 Vue 实例，得到 ViewModel
            var vm = new Vue({
                el: '#app',
                data: {
                    msg: '曾经，我也是一个单纯的少年，单纯的我，傻傻的问，谁是世界上最单纯的男人'
                },
                methods: {},
                //定义私用局部过滤器。只能在当前 vue 对象中使用
                filters: {
                    dataFormat(msg) {
                        return msg+'xxxxx';
                    }
                }
            });
 *
 *  Object.keys() 方法会返回一个由给定对象的自身可枚举属性组成的数组，
 *  数组中属性名的排列顺序和使用 for...in 循环遍历该对象时返回的顺序一致 （两者的主要区别是 一个 for-in 循环还会枚举其原型链上的属性）。
    循环绑定到Vue.filter上:

    第一步:编写filter.js
    第二步:main.js中引入
    第三部:vue实例中使用 ==>{{COURSE_LEVEL | optionKV(course.level)}}
 */
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});

/**
 * axios拦截器
 */
Axios.interceptors.request.use((config)=>{
  console.log("请求:",config);
  return config;
},error => {});
Axios.interceptors.response.use((response)=>{
  console.log("响应:",response);
  return response;
},error => {});


new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
