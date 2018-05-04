/**
 * @file: index.
 * @intro: api请求索引.
 * @author: zzmhot.
 * @email: zzmhot@163.com.
 * @Date: 2017/5/8 15:31.
 * @Copyright(©) 2017 by zzmhot.
 *
 */

//导入模块
import * as api_user from './user'
import * as api_menu from './menu'
import * as api_role from './role'
import * as api_resource from './resource'
import * as api_sessions from './sessions'
import * as api_cache from './cache'
import * as api_content from './content'
import * as api_export from './export'
const apiObj = {
  api_user,
  api_menu,
  api_role,
  api_resource,
  api_sessions,
  api_cache,
  api_content,
  api_export
}

const install = function (Vue) {
  if (install.installed) return
  install.installed = true

  //定义属性到Vue原型中
  Object.defineProperties(Vue.prototype, {
    $fetch: {
      get() {
        return apiObj
      }
    }
  })
}

export default {
  install
}
