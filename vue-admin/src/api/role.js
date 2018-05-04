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
import {port_role} from 'common/port_uri'

//角色管理 分页查询
export function page(data) {
  return fetch({
    url: port_role.page+"/"+data.length+"/"+data.page,
    method: 'post',
    data
  })
}

//点击编辑获取单条记录
export function get(data) {
  return fetch({
    url: port_role.get+"/"+data.id,
    method: 'get',
    data
  })
}
//保存记录
export function save(data) {
  return fetch({
    url: port_role.save,
    method: 'post',
    data
  })
}
//删除记录
export function del(data) {
  return fetch({
    url: port_role.del+"/"+data.id,
    method: 'get'
  })
}

export function relate(data){
  return fetch({
    url: port_role.relate+"/"+data.id,
    method: 'get'
  })
}

export function submit_relate(data){
  let id = data.id
  data = data.permissionIds
  return fetch({
    url: port_role.relate+"/"+id,
    method: 'post',
    data
  })
}
