import Vue from 'vue'
import App from './app.vue'
import router from './router'
//引入axios
import Axios from "axios";//Alt+Enter加入字典中

Vue.config.productionTip = false;
//发送请求的全局变量
Vue.prototype.$ajax = Axios;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
