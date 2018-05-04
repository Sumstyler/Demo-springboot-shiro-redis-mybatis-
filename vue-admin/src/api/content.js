/**
 * @file: file.
 * @intro: file请求数据配置.
 * @author: zzmhot.
 * @email: zzmhot@163.com.
 * @Date: 2017/5/8 15:31.
 * @Copyright(©) 2017 by zzmhot.
 *
 */

import fetch from 'common/fetch'
import {port_content} from 'common/port_uri'

//内容管理 分页查询
export function page(data) {
  return fetch({
    url: port_content.page+"/"+data.length+"/"+data.page,
    method: 'post',
    data
  })
}
