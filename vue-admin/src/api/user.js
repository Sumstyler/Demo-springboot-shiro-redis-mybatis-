/**
 * @file: user.
 * @intro: 用户请求数据配置.
 * @author: zzmhot.
 * @email: zzmhot@163.com.
 * @Date: 2017/5/8 15:18.
 * @Copyright(©) 2017 by zzmhot.
 *
 */

import fetch from 'common/fetch'
import {port_user} from 'common/port_uri'

//登录
export function login(data) {
  return fetch({
    url: port_user.login,
  method: 'post',
  data
})
}
//登出
export function logout() {
  return fetch({
    url: port_user.logout,
    method: 'get'
  })
}
//用户管理 分页查询
export function page(data) {
  return fetch({
    url: port_user.page+"/"+data.length+"/"+data.page,
    method: 'post',
    data
  })
}
//点击编辑获取单条记录
export function get(data) {
  return fetch({
    url: port_user.get+"/"+data.id,
    method: 'get',
    data
  })
}
//保存记录
export function save(data) {
  return fetch({
    url: port_user.save,
    method: 'post',
    data
  })
}
//删除记录
export function del(data) {
  return fetch({
    url: port_user.del+"/"+data.id,
    method: 'get'
  })
}

//关联角色
export function relate(data) {
  return fetch({
    url: port_user.relate+"/"+data.id,
    method: 'get'
  })
}

//关联角色
export function submit_relate(data) {
  let id = data.id
  data = data.roleIds
  return fetch({
    url: port_user.relate+"/"+id,
    method: 'post',
    data
  })
}
