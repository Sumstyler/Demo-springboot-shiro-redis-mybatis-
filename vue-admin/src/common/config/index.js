/**
 * @file: index.
 * @intro: 全局配置文件.
 * @author: zzmhot.
 * @email: zzmhot@163.com.
 * @Date: 2017/5/8 16:38.
 * @Copyright(©) 2017 by thinkive.
 *
 */

//本地存储的前缀
export const storage_prefix = 'zzm_admin_storage_'
//base64加密前缀
export const base64_prefix = 'zzm_admin_base64_'
//接入服务器接口地址根目录   pro 配置为 /
export const server_base_url = 'http://127.0.0.1:8080/'

export const upload_url = server_base_url+ "api/file/upload"

export const download_url = server_base_url + "api/file/download/"

export const export_template_url=server_base_url + "api/export/upload"

export const export_template_download_url = server_base_url + "api/export/download"
