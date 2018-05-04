/**
 * Created by zzmhot on 2017/3/23.
 *
 * 路由Map
 *
 * @author: zzmhot
 * @github: https://github.com/zzmhot
 * @email: zzmhot@163.com
 * @Date: 2017/3/23 18:30
 * @Copyright(©) 2017 by zzmhot.
 *
 */

import Vue from 'vue'
import VueRouter from 'vue-router'
import store from 'store'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

//import components
//view page warp component
import viewPageComponent from 'pages/App'

//home
import homeComponent from 'pages/home'
//404
import noPageComponent from 'pages/error/404'
//login
import loginComponent from 'pages/user/login'
//用户管理
import userTableComponent from 'pages/user/table'
import userSaveComponent from 'pages/user/save'
import relateRoleComponent from 'pages/user/relateRole'
//角色管理
import roleTableComponent from 'pages/role/table'
import roleSaveComponent from 'pages/role/save'
import roleRelateComponent from 'pages/role/relate'
//资源管理
import resourceTableComponent from 'pages/resource/table'
import resourceSaveComponent from 'pages/resource/save'
// 会话管理
import sessionsTableComponent from 'pages/sessions/table'
//内容管理
import contentTableComponent from 'pages/content/table'
//导出配置
import exportTableComponent from 'pages/export/table'
import exportSaveComponent from 'pages/export/save'
//bar charts
import barChartsComponent from 'pages/charts/bar'



Vue.use(VueRouter)

//使用AMD方式加载
// component: resolve => require(['pages/home'], resolve),
const routes = [{
  path: '/404',
  name: 'notPage',
  component: noPageComponent
}, {
  path: '*',
  redirect: '/404'
}, {
  path: '/user/login',
  name: 'login',
  component: loginComponent
}, {
  path: '/',
  redirect: '/home',
  component: viewPageComponent,
  children: [{
    path: '/home',
    name: 'home',
    component: homeComponent,
    meta: {
      title: "主页",
      auth: true
    }
  }, {
    path: '/user/manager',
    name: 'userManager',
    component: userTableComponent,
    meta: {
      title: "用户管理",
      auth: true
    }
  }, {
    path: '/user/save/:id',
    name: 'userUpdate',
    component: userSaveComponent,
    meta: {
      title: "用户修改",
      auth: true
    }
  }, {
    path: '/user/add',
    name: 'userAdd',
    component: userSaveComponent,
    meta: {
      title: "添加用户",
      auth: true
    }
  }, {
    path: '/role/manager',
    name: 'roleManger',
    component: roleTableComponent,
    meta: {
      title: "角色管理",
      auth: true
    }
  }, {
    path: '/role/save/:id',
    name: 'roleUpdate',
    component: roleSaveComponent,
    meta: {
      title: "角色修改",
      auth: true
    }
  }, {
    path: '/role/add',
    name: 'roleAdd',
    component: roleSaveComponent,
    meta: {
      title: "添加角色",
      auth: true
    }
  }, {
    path: '/role/relate/:id',
    name: 'roleRelate',
    component: roleRelateComponent,
    meta: {
      title: "关联资源",
      auth: true
    }
  }, {
    path: '/resource/manager',
    name: 'resourceManger',
    component: resourceTableComponent,
    meta: {
      title: "资源管理",
      auth: true
    }
  }, {
    path: '/resource/save/:id',
    name: 'resourceUpdate',
    component: resourceSaveComponent,
    meta: {
      title: "资源修改",
      auth: true
    }
  }, {
    path: '/resource/add',
    name: 'resourceAdd',
    component: resourceSaveComponent,
    meta: {
      title: "添加资源",
      auth: true
    }
  }, {
    path: '/sessions/manager',
    name: 'sessionsManger',
    component: sessionsTableComponent,
    meta: {
      title: "会话管理",
      auth: true
    }
  }, {
    path: '/content/table',
    name: 'contentManager',
    component: contentTableComponent,
    meta: {
      title: "内容管理",
      auth: true
    }
  }, {
    path: '/export/table',
    name: 'exportManager',
    component: exportTableComponent,
    meta: {
      title: "导出配置",
      auth: true
    }
  }, {
    path: '/export/add',
    name: 'exportAdd',
    component: exportSaveComponent,
    meta: {
      title: "导出配置添加",
      auth: true
    }
  }, {
    path: '/export/save',
    name: 'exportUpdate',
    component: exportSaveComponent,
    meta: {
      title: "导出配置修改",
      auth: true
    }
  },{
    path: '/charts/bar',
    name: 'chartsBar',
    component: barChartsComponent,
    meta: {
      title: "柱状图表",
      auth: true
    }
  }]
}]

const router = new VueRouter({
  routes,
  mode: 'hash', //default: hash ,history
  scrollBehavior (to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return {x: 0, y: 0}
    }
  }
})

//全局路由配置
//路由开始之前的操作
router.beforeEach((to, from, next) => {
  NProgress.done().start()
  let toName = to.name
  // let fromName = from.name
  let is_login = store.state.user_info.login

  if (!is_login && toName !== 'login') {
    next({
      name: 'login'
    })
  } else {
    if (is_login && toName === 'login') {
      next({
        path: '/'
      })
    } else {
      next()
    }
  }
})

//路由完成之后的操作
router.afterEach(route => {
  NProgress.done()
})

export default router
