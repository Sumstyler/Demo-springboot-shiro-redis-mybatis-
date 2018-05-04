
import fetch from 'common/fetch'
import {port_sessions} from 'common/port_uri'

//会话管理 分页查询
export function online(data) {
  return fetch({
    url: port_sessions.online,
    method: 'get'
  })
}

export function kickedout(data) {
  return fetch({
    url: port_sessions.kickedout+"/"+data.username,
    method: 'get'
  })
}

