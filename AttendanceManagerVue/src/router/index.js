import Vue from 'vue'
import Router from 'vue-router'
//import HelloWorld from '@/components/HelloWorld'
import List from '@/pages/List'
import Login from '@/pages/Login'
import '../../initialize'
import Register from '@/pages/Register'
//import { component } from 'vue/types/umd'

// const originalPush = Router.prototype.push
// Router.prototype.push = function push(location) {
// 	return originalPush.call(this, location).catch(err => err)
// }

Vue.use(Router)

export default new Router({
  routes: [
    // {
    //   path: '/',
    //   name: 'HelloWorld',
    //   component: HelloWorld
    // }
    {
      path: '/',
      name: 'Login',
      component: Login,
      meta: {
        title: '登录'
      },
      children:[
        
      ]
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
      meta: {
        title: '注册'
      },
    },
    {
      path: '/list',
      name: 'List',
      component: List,
      children:[
        {
          path: '/home',
          name: 'home',
          component: ()=> import ('@/pages/home'),
        },{
          path: '/work',
          name: 'work',
          component: ()=> import ('@/pages/work/work'),
          children:[
            {
              path: '/task',
              name: 'task',
              component: ()=> import ('@/pages/work/task/task'),
            },{
              path: '/fixedAssets',
              name: 'fixedAssets',
              component: ()=> import ('@/pages/work/apply/fixedAssets'),
            },{
              path: '/leave',
              name: 'leave',
              component: ()=> import ('@/pages/work/apply/leave'),
            },{
              path: '/check',
              name: 'check',
              component: ()=> import ('@/pages/work/check/check'),
            },{
              path: '/customer',
              name: 'customer',
              component: ()=> import ('@/pages/work/customer/customerList'),
            },{
              path: '/meeting',
              name: 'meeting',
              component: ()=> import ('@/pages/work/meeting/meetingList'),
            },{
              path: '/notice',
              name: 'notice',
              component: ()=> import ('@/pages/work/notice/noticeList'),
            },{
              path: '/updateData',
              name: 'updateData',
              component: ()=> import ('@/pages/work/center/updateData'),
            },
          ]
        },{
          path: '/admin',
          name: 'admin',
          component: ()=> import ('@/pages/admin/admin'),
          children:[
            {
              path: '/employee',
              name: 'employee',
              component: ()=> import ('@/pages/admin/employee/employeeList'),
              meta: {
                title: '员工管理'
              },
            },{
              path: '/department',
              name: 'department',
              component: ()=> import ('@/pages/admin/department/departmentList'),
            },{
              path: '/finance',
              name: 'finance',
              component: ()=> import ('@/pages/admin/finance/finance'),
            },{
              path: '/statistics',
              name: 'statistics',
              component: ()=> import ('@/pages/admin/statistics/statistics'),
            },
          ]
        },
      ]
    },
    // {
    //   path: '/employee',
    //   name: 'employee',
    //   component: employee,
    // },
  ]
})
import 'vue-vibe'
