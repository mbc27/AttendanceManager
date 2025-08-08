// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import * as G2 from '@antv/g2';
import '@/../initialize'
import Print from 'vue-print-nb'

import axios from "./axios/axios.js";
Vue.prototype.axios = axios;


// const cors = require('koa2-cors');
// App.use(cors());

Vue.config.productionTip = false

Vue.use(ElementUI);

Vue.use(G2);

Vue.use(Print);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
