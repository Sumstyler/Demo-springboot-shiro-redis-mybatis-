
import fetch from 'common/fetch'
import {port_export} from 'common/port_uri'

export function page(data) {
  return fetch({
    url: port_export.page+"/"+data.length+"/"+data.page,
    method: 'post',
    data
  })
}

//保存记录
export function save(data) {
  return fetch({
    url: port_export.save,
    method: 'post',
    data
  })
}

//删除记录
export function del(data) {
  return fetch({
    url: port_export.del+"/"+data.id,
    method: 'get'
  })
}

//获取单条记录
export function get(data) {
  return fetch({
    url: port_export.get+"/"+data.id,
    method: 'get'
  })
}
