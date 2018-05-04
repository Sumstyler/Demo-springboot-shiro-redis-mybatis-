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
import {port_menu} from 'common/port_uri'

//获取左侧菜单
export function menu(data) {
  return fetch({
    url: port_menu.menu,
    method: 'get',
    data
  })
}
