
import fetch from 'common/fetch'
import {port_cache} from 'common/port_uri'

export function clearAll() {
  return fetch({
    url: port_cache.clearAll,
    method: 'get'
  })
}

